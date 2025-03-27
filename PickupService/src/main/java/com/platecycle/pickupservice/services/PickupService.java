package com.platecycle.pickupservice.services;

import com.platecycle.pickupservice.model.Delivery;
import com.platecycle.pickupservice.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PickupService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery createDelivery(Delivery delivery) {
        if (delivery.getId() == null) {
            delivery.setId(UUID.randomUUID());
        }
        delivery.setCreatedAt(Instant.now());
        delivery.setUpdatedAt(Instant.now());
        return deliveryRepository.save(delivery);
    }

    public Delivery getDelivery(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery updateDelivery(UUID id, Delivery updatedDelivery) {
        Delivery existing = getDelivery(id);
        if (updatedDelivery.getReservationId() != null) {
            existing.setReservationId(updatedDelivery.getReservationId());
        }
        if (updatedDelivery.getPickupMethod() != null) {
            existing.setPickupMethod(updatedDelivery.getPickupMethod());
        }
        if (updatedDelivery.getStatus() != null) {
            existing.setStatus(updatedDelivery.getStatus());
        }
        if (updatedDelivery.getScheduledPickupTime() != null) {
            existing.setScheduledPickupTime(updatedDelivery.getScheduledPickupTime());
        }
        if (updatedDelivery.getDeliveredTime() != null) {
            existing.setDeliveredTime(updatedDelivery.getDeliveredTime());
        }
        existing.setUpdatedAt(Instant.now());
        return deliveryRepository.save(existing);
    }

    public void deleteDelivery(UUID id) {
        deliveryRepository.deleteById(id);
    }
}
