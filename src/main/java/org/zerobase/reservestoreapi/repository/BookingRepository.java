package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
