package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/product")
    public String productFallback() {
        return "⚠️ Product Service is down. Please try again later.";
    }

    @GetMapping("/fallback/order")
    public String orderFallback() {
        return "⚠️ Order Service is down. Please try again later.";
    }
}