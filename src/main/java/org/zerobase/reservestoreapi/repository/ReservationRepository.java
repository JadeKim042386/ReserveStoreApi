package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
