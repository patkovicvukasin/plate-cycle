package com.platecycle.productservice.services;

import com.platecycle.productservice.model.Product;
import com.platecycle.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return productRepository.save(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
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
        if (updatedProduct.getExpirationDate() != null) {
            existing.setExpirationDate(updatedProduct.getExpirationDate());
        }
        if (updatedProduct.getCategory() != null) {
            existing.setCategory(updatedProduct.getCategory());
        }
        existing.setUpdatedAt(Instant.now());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public void reduceQuantity(Long productId, int amount) {
        Product product = getProduct(productId);
        if (product.getQuantity() < amount) {
            throw new RuntimeException("Insufficient product quantity. Available: " + product.getQuantity());
        }
        product.setQuantity(product.getQuantity() - amount);
        product.setUpdatedAt(Instant.now());
        productRepository.save(product);
    }
}
