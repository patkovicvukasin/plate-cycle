package com.platecycle.productservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products", schema = "public")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "city", nullable = false, length = 100)
    private String city;


    @NotNull
    @Column(name = "self_delivery", nullable = false)
    private Boolean selfDelivery;

    @NotNull
    @Column(name = "donor_id", nullable = false)
    private UUID donorId;

    @Size(max = 20)
    @NotNull
    @Column(name = "delivery_option", nullable = false, length = 20)
    private String deliveryOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Getteri i Setteri

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Instant getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() { return city;}

    public void setCity(String city) { this.city = city;}

    public Boolean getSelfDelivery() {
        return selfDelivery;
    }

    public void setSelfDelivery(Boolean selfDelivery) {
        this.selfDelivery = selfDelivery;
    }

    public UUID getDonorId() {
        return donorId;
    }
    public void setDonorId(UUID donorId) {
        this.donorId = donorId;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }
    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
        // Ako je deliveryOption "SELF_DELIVERY", postavi selfDelivery na true, inače na false.
        if ("SELF_DELIVERY".equalsIgnoreCase(deliveryOption)) {
            this.selfDelivery = true;
        } else if ("NO_DELIVERY".equalsIgnoreCase(deliveryOption)) {
            this.selfDelivery = false;
        }
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }



}
