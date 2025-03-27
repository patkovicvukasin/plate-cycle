package com.platecycle.reservationsservice.clients;

import com.platecycle.reservationsservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "ProductService")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable("id") UUID id);

    @PutMapping("/api/products/{id}/reduce-quantity")
    void reduceQuantity(@PathVariable("id") UUID id, @RequestParam("amount") Integer amount);
}
