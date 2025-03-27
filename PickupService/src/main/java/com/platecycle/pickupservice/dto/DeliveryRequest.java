package com.platecycle.pickupservice.dto;

import java.util.UUID;

public class DeliveryRequest {
    private UUID reservationId;
    private String deliveryCity; // Dodato polje

    public UUID getReservationId() {
        return reservationId;
    }
    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }
}
