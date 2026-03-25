package com.wipro.productservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.wipro.productservice.entity.Product;
import com.wipro.productservice.repository.ProductRepository;
import com.wipro.productservice.dto.OrderEvent;

@Service
public class KafkaConsumerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; 

    @KafkaListener(topics = "order-topic", groupId = "product-group")
    public void consume(OrderEvent event) {

        Product product = productRepository.findById(event.getProductId()).orElse(null);

        if (product == null) {
            System.out.println(" Product not found for ID: " + event.getProductId());
            return;
        }

        switch (event.getEvent()) {

            case "ORDER_CREATED":
                handleOrderCreated(event, product);
                break;

            case "ORDER_CANCELLED":
                handleOrderCancelled(event, product);
                break;

            default:
                System.out.println("Unknown event type: " + event.getEvent());
        }
    }

    private void handleOrderCreated(OrderEvent event, Product product) {

        int updatedQty = product.getQuantity() - event.getQuantity();
        product.setQuantity(updatedQty);
        productRepository.save(product);

        String message = "id = " + event.getProductId() + ", updatedStock = " + updatedQty;

        System.out.println(message); 

        kafkaTemplate.send("stock-topic", message); 
    }

  
    private void handleOrderCancelled(OrderEvent event, Product product) {

        int updatedQty = product.getQuantity() + event.getQuantity();
        product.setQuantity(updatedQty);
        productRepository.save(product);

        String message = "id = " + event.getProductId() + ", updatedStock = " + updatedQty;

        System.out.println(message); 

        kafkaTemplate.send("stock-topic", message); 
    }
}