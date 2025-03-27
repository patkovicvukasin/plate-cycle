package com.platecycle.reservationsservice.repositories;

import com.platecycle.reservationsservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    // Možeš dodati custom upite, npr. findByUserId, findByStatus itd.
}
