package com.platecycle.productservice.repositories;

import com.platecycle.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // Možeš dodati custom upite ako je potrebno
}
