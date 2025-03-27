package com.platecycle.productservice.services;

import com.platecycle.productservice.model.Product;
import com.platecycle.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return productRepository.save(product);
    }


    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        if (updatedProduct.getTitle() != null) {
            existing.setTitle(updatedProduct.getTitle());
        }
        if (updatedProduct.getDescription() != null) {
            existing.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getQuantity() != null) {
            existing.setQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.getStatus() != null) {
            existing.setStatus(updatedProduct.getStatus());
        }
        if (updatedProduct.getDeliveryOption() != null) {
            existing.setDeliveryOption(updatedProduct.getDeliveryOption());
        }
        if (updatedProduct.getExpirationDate() != null) {
            existing.setExpirationDate(updatedProduct.getExpirationDate());
        }
        // Ažuriraj kategoriju ako je prosleđena (ova logika pretpostavlja da je u JSON-u unet ceo objekat kategorije)
        if (updatedProduct.getCategory() != null) {
            existing.setCategory(updatedProduct.getCategory());
        }
        existing.setUpdatedAt(Instant.now());
        return productRepository.save(existing);
    }



    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    public void reduceQuantity(UUID productId, int amount) {
        Product product = getProduct(productId); // koristi već implementiranu metodu getProduct
        if (product.getQuantity() < amount) {
            throw new RuntimeException("Insufficient product quantity. Available: " + product.getQuantity());
        }
        product.setQuantity(product.getQuantity() - amount);
        product.setUpdatedAt(Instant.now());
        productRepository.save(product);
    }

}
