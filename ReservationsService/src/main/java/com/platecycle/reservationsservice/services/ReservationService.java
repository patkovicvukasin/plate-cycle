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

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PickupClient pickupClient;

    public Reservation createReservation(ReservationRequest request) {
        ProductDTO product = productClient.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found: " + request.getProductId());
        }
        if (product.getQuantity() < request.getQuantityRequested()) {
            throw new RuntimeException("Insufficient product quantity. Available: " + product.getQuantity());
        }

        Reservation reservation = new Reservation();
        reservation.setProductId(request.getProductId());
        reservation.setRecipientId(request.getRecipientId());
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());
        reservation.setPickupOption(request.getPickupOption());
        reservation.setStatus("COMPLETED");

        Reservation savedReservation = reservationRepository.save(reservation);

        if (request.getPickupOption() == PickupOption.DELIVERY && !product.isDeliveryIncluded()) {
            DeliveryRequest deliveryRequest = new DeliveryRequest();
            deliveryRequest.setReservationId(savedReservation.getId());
            System.out.println("Product city: " + product.getCity());
            deliveryRequest.setDeliveryCity(product.getCity());
            pickupClient.createDelivery(deliveryRequest);
        }
        productClient.reduceQuantity(request.getProductId(), request.getQuantityRequested());
        return savedReservation;
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + reservationId));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
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

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public ProductDTO fetchProductDetails(Long productId) {
        return productClient.getProductById(productId);
    }

}
