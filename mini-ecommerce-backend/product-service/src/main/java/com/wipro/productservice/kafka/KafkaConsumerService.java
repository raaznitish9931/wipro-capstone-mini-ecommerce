package com.wipro.productservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.wipro.productservice.entity.Product;
import com.wipro.productservice.repository.ProductRepository;
import com.wipro.productservice.dto.OrderEvent;

import java.time.format.DateTimeFormatter;

@Service
public class KafkaConsumerService {

    @Autowired
    private ProductRepository productRepository;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @KafkaListener(topics = "order-topic", groupId = "product-group")
    public void consume(OrderEvent event) {

        Product product = productRepository.findById(event.getProductId()).orElse(null);

        if (product == null) {
            System.out.println("\n❌ Product not found for ID: " + event.getProductId());
            return;
        }

        String formattedTime = event.getTimestamp().format(formatter);

        switch (event.getEvent()) {

            case "ORDER_CREATED":
                handleOrderCreated(event, product, formattedTime);
                break;

            case "ORDER_CANCELLED":
                handleOrderCancelled(event, product, formattedTime);
                break;

            default:
                System.out.println("\n⚠️ Unknown event type: " + event.getEvent());
        }
    }

    // ================= ORDER CREATED =================
    private void handleOrderCreated(OrderEvent event, Product product, String time) {

        int updatedQty = product.getQuantity() - event.getQuantity();
        product.setQuantity(updatedQty);
        productRepository.save(product);

        System.out.println("\n[" + time + "]");
        System.out.println("=======================================");
        System.out.println("🟢 Order Created Event Received");
        System.out.println("---------------------------------------");
        System.out.println("Product       : " + event.getProductName());
        System.out.println("Product ID    : " + event.getProductId());
        System.out.println("Quantity Used : " + event.getQuantity());
        System.out.println("Updated Stock : " + updatedQty);
        System.out.println("Status        : SUCCESS");
        System.out.println("=======================================\n");
    }

    // ================= ORDER CANCELLED =================
    private void handleOrderCancelled(OrderEvent event, Product product, String time) {

        int updatedQty = product.getQuantity() + event.getQuantity();
        product.setQuantity(updatedQty);
        productRepository.save(product);

        System.out.println("\n[" + time + "]");
        System.out.println("=======================================");
        System.out.println("🔴 Order Cancelled Event Received");
        System.out.println("---------------------------------------");
        System.out.println("Product       : " + event.getProductName());
        System.out.println("Product ID    : " + event.getProductId());
        System.out.println("Stock Added   : " + event.getQuantity());
        System.out.println("Updated Stock : " + updatedQty);
        System.out.println("Status        : RESTORED");
        System.out.println("=======================================\n");
    }
}