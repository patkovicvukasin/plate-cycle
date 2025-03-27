package com.platecycle.reservationsservice.dto;

import com.platecycle.reservationsservice.model.PickupOption;

import java.util.UUID;

public class ReservationRequest {
    private UUID productId;
    private UUID recipientId;
    private int quantityRequested; // koliko jedinica proizvoda se rezerviše
    private PickupOption pickupOption;  // npr. "SELF_DELIVERY" ili "DELIVERY"

    // Getteri i setteri
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public UUID getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }
    public int getQuantityRequested() {
        return quantityRequested;
    }
    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }
    public PickupOption getPickupOption() {
        return pickupOption;
    }
    public void setPickupOption(PickupOption pickupOption) {
        this.pickupOption = pickupOption;
    }
}
