package com.platecycle.reservationsservice.services;

import com.platecycle.reservationsservice.dto.DeliveryResponse;
import com.platecycle.reservationsservice.dto.DeliveryRequest;
import com.platecycle.reservationsservice.clients.PickupClient;
import com.platecycle.reservationsservice.clients.ProductClient;
import com.platecycle.reservationsservice.dto.ProductDTO;
import com.platecycle.reservationsservice.dto.ReservationRequest;
import com.platecycle.reservationsservice.model.PickupOption;
import com.platecycle.reservationsservice.model.Reservation;
import com.platecycle.reservationsservice.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PickupClient pickupClient;

    // Kreiranje rezervacije
    public Reservation createReservation(ReservationRequest request) {
        // Korak 1: Proveri da li proizvod postoji i ima dovoljno količine
        ProductDTO product = productClient.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found: " + request.getProductId());
        }
        if (product.getQuantity() < request.getQuantityRequested()) {
            throw new RuntimeException("Insufficient product quantity. Available: " + product.getQuantity());
        }

        // Opcionalno: Proveri korisnika preko userClient, npr.:
        // UserDTO user = userClient.getUserById(request.getRecipientId());
        // if(user == null) { ... }

        // Korak 2: Kreiraj rezervaciju
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID());
        reservation.setProductId(request.getProductId());
        reservation.setRecipientId(request.getRecipientId());
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());
        // Postavi pickupOption – možeš dodati logiku da konvertuješ string u enum ako koristiš enum
        reservation.setPickupOption(request.getPickupOption());
        // Postavi inicijalni status rezervacije
        reservation.setStatus("PENDING");

        Reservation savedReservation = reservationRepository.save(reservation);

        // Ako pickupOption je DELIVERY, pozovi Pickup-service za kreiranje dostave
        if (request.getPickupOption() == PickupOption.DELIVERY) {
            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setReservationId(savedReservation.getId());
            System.out.println("Product city: " + product.getCity());
            deliveryRequest.setDeliveryCity(product.getCity());


            // Možeš dodati i podatke o gradu, npr. iz podataka o donoru ili proizvodu
            // deliveryRequest.setCity(...);
            pickupClient.createDelivery(deliveryRequest);
        }
        // Takođe, pozovi productClient.reduceQuantity(...) za smanjenje količine proizvoda
        productClient.reduceQuantity(request.getProductId(), request.getQuantityRequested());

        return savedReservation;
    }



    // Dohvat rezervacije po ID
    public Reservation getReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + reservationId));
    }

    // Dohvat svih rezervacija
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Ažuriranje rezervacije
    public Reservation updateReservation(UUID reservationId, Reservation updatedReservation) {
        Reservation existing = getReservation(reservationId);
        if (updatedReservation.getProductId() != null) {
            existing.setProductId(updatedReservation.getProductId());
        }
        if (updatedReservation.getRecipientId() != null) {
            existing.setRecipientId(updatedReservation.getRecipientId());
        }
        if (updatedReservation.getPickupOption() != null) {
            existing.setPickupOption(updatedReservation.getPickupOption());
        }
        if (updatedReservation.getStatus() != null) {
            existing.setStatus(updatedReservation.getStatus());
        }
        existing.setUpdatedAt(Instant.now());
        return reservationRepository.save(existing);
    }

    // Brisanje rezervacije
    public void deleteReservation(UUID reservationId) {
        reservationRepository.deleteById(reservationId);
    }


    public ProductDTO fetchProductDetails(UUID productId) {
        return productClient.getProductById(productId);
    }

}
