package com.platecycle.pickupservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    // Polje za naziv dostavne kompanije – ovo može biti default vrednost
    @Size(max = 100)
    @NotNull
    @Column(name = "delivery_company", nullable = false, length = 100)
    private String deliveryCompany;

    // Polje za grad, preuzeto iz Product servisa (donor unosi prilikom kreiranja oglasa)
    @Size(max = 100)
    @NotNull
    @Column(name = "delivery_city", nullable = false, length = 100)
    private String deliveryCity;

    @Size(max = 20)
    @NotNull
    @Column(name = "pickup_method", nullable = false, length = 20)
    private String pickupMethod;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "scheduled_pickup_time")
    private Instant scheduledPickupTime;

    @Column(name = "delivered_time")
    private Instant deliveredTime;

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

    public UUID getReservationId() {
        return reservationId;
    }
    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }
    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getPickupMethod() {
        return pickupMethod;
    }
    public void setPickupMethod(String pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getScheduledPickupTime() {
        return scheduledPickupTime;
    }
    public void setScheduledPickupTime(Instant scheduledPickupTime) {
        this.scheduledPickupTime = scheduledPickupTime;
    }

    public Instant getDeliveredTime() {
        return deliveredTime;
    }
    public void setDeliveredTime(Instant deliveredTime) {
        this.deliveredTime = deliveredTime;
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
