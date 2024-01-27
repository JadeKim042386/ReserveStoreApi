package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByCreatedAtAndStoreId(LocalDateTime requestTime, Long storeId);

    boolean existsByCreatedByAndStoreId(String username, Long storeId);

    Optional<Booking> findByCreatedByAndStoreId(String username, Long storeId);

    List<Booking> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
