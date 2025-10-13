package com.platecycle.pickupservice.services;

import com.platecycle.pickupservice.model.Delivery;
import com.platecycle.pickupservice.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DeliverySimulationService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Async
    public void simulateDelivery(Long deliveryId) {
        try {
            System.out.println("🚚 Simulating delivery for ID " + deliveryId + "...");
            Thread.sleep(30_000);

            Delivery delivery = deliveryRepository.findById(deliveryId)
                    .orElseThrow(() -> new RuntimeException("Delivery not found: " + deliveryId));

            delivery.setStatus("COMPLETED");
            delivery.setUpdatedAt(Instant.now());
            deliveryRepository.save(delivery);

            System.out.println("✅ Delivery " + deliveryId + " marked as COMPLETED.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
