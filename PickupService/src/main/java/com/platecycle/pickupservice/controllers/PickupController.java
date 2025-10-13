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

@RestController
@RequestMapping("/api/pickup")
public class PickupController {

    @Autowired
    private PickupService pickupService;

    @PostMapping("/deliveries")
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryRequest request) {
        Delivery created = pickupService.createDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/deliveries")
    public List<Delivery> getAllDeliveries() {
        return pickupService.getAllDeliveries();
    }

    @GetMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
        Delivery delivery = pickupService.getDelivery(id);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery delivery) {
        Delivery updated = pickupService.updateDelivery(id, delivery);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deliveries/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        pickupService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
