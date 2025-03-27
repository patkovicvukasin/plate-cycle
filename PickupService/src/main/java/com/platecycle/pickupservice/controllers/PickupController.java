package com.platecycle.pickupservice.controllers;

import com.platecycle.pickupservice.dto.DeliveryRequest;
import com.platecycle.pickupservice.model.Delivery;
import com.platecycle.pickupservice.repositories.DeliveryRepository;
import com.platecycle.pickupservice.services.PickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pickup")
public class PickupController {

    @Autowired
    private PickupService pickupService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostMapping("/deliveries")
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryRequest request) {
        System.out.println("Received delivery request with city: " + request.getDeliveryCity());
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setReservationId(request.getReservationId());
        delivery.setDeliveryCity(request.getDeliveryCity()); // Ovo mora biti postavljeno
        delivery.setDeliveryCompany("DefaultDeliveryCo");
        delivery.setPickupMethod("DefaultMethod");
        delivery.setStatus("PENDING");
        delivery.setCreatedAt(Instant.now());
        delivery.setUpdatedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryRepository.save(delivery));
    }


    // Dohvat svih dostava
    @GetMapping("/deliveries")
    public List<Delivery> getAllDeliveries() {
        return pickupService.getAllDeliveries();
    }

    // Dohvat jedne dostave
    @GetMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable UUID id) {
        Delivery delivery = pickupService.getDelivery(id);
        return ResponseEntity.ok(delivery);
    }

    // Ažuriranje dostave
    @PutMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable UUID id, @RequestBody Delivery delivery) {
        Delivery updated = pickupService.updateDelivery(id, delivery);
        return ResponseEntity.ok(updated);
    }

    // Brisanje dostave
    @DeleteMapping("/deliveries/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable UUID id) {
        pickupService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }



}
