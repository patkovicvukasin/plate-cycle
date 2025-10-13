package com.platecycle.reservationsservice.dto;

import com.platecycle.reservationsservice.model.PickupOption;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReservationRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Long recipientId;

    @Min(1)
    private int quantityRequested;

    @NotNull
    private PickupOption pickupOption;

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(Long recipientId) {
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
