package org.zerobase.reservestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Booking;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  boolean existsByCreatedAtAndStoreId(LocalDateTime requestTime, Long storeId);

  boolean existsByCreatedByAndStoreId(String username, Long storeId);

  Optional<Booking> findByCreatedByAndStoreId(String username, Long storeId);

  Page<Booking> findAllByCreatedAtBetweenAndStoreId(
      LocalDateTime startDate, LocalDateTime endDate, Long storeId, Pageable pageable);
}
