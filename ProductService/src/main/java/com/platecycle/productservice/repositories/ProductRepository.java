package com.platecycle.productservice.repositories;

import com.platecycle.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Custom upiti, npr.:
    // List<Product> findByCategoryId(UUID categoryId);
}
