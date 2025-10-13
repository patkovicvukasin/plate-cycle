package com.platecycle.pickupservice.services;

import com.platecycle.pickupservice.dto.DeliveryRequest;
import com.platecycle.pickupservice.model.Delivery;
import com.platecycle.pickupservice.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PickupService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliverySimulationService deliverySimulationService;

    public Delivery createDelivery(DeliveryRequest request) {
        System.out.println("Received delivery request for city: " + request.getDeliveryCity());

        Delivery delivery = new Delivery();
        delivery.setReservationId(request.getReservationId());
        delivery.setDeliveryCity(request.getDeliveryCity());
        delivery.setDeliveryCompany("DefaultDeliveryCo");
        delivery.setStatus("PENDING");
        delivery.setCreatedAt(Instant.now());
        delivery.setUpdatedAt(Instant.now());

        Delivery saved = deliveryRepository.save(delivery);

        deliverySimulationService.simulateDelivery(saved.getId());

        return saved;
    }

    public Delivery getDelivery(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery updateDelivery(Long id, Delivery updatedDelivery) {
        Delivery existing = getDelivery(id);
        if (updatedDelivery.getReservationId() != null) {
            existing.setReservationId(updatedDelivery.getReservationId());
        }
        if (updatedDelivery.getStatus() != null) {
            existing.setStatus(updatedDelivery.getStatus());
        }
        existing.setUpdatedAt(Instant.now());
        return deliveryRepository.save(existing);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
