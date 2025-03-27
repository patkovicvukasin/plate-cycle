package com.platecycle.reservationsservice.controllers;

import com.platecycle.reservationsservice.dto.ProductDTO;
import com.platecycle.reservationsservice.dto.ReservationRequest;
import com.platecycle.reservationsservice.model.Reservation;
import com.platecycle.reservationsservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // POST /api/reservations – kreiranje rezervacije
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        Reservation created = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/reservations – dohvat svih rezervacija
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // GET /api/reservations/{id} – dohvat jedne rezervacije
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable UUID id) {
        return reservationService.getReservation(id);
    }

    // PUT /api/reservations/{id} – ažuriranje rezervacije
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable UUID id, @RequestBody Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/reservations/{id} – brisanje rezervacije
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/test-product/{productId}")
    public ResponseEntity<ProductDTO> testProductClient(@PathVariable UUID productId) {
        ProductDTO productDTO = reservationService.fetchProductDetails(productId);
        return ResponseEntity.ok(productDTO);
    }
}
