package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.domain.QBooking;
import org.zerobase.reservestoreapi.repository.custom.CustomBookingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository
        extends JpaRepository<Booking, Long>,
                CustomBookingRepository,
                QuerydslBinderCustomizer<QBooking> {

    @Override
    default void customize(QuerydslBindings bindings, QBooking root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.approve, root.createdAt);
        bindings.bind(root.approve)
                .as("approve")
                .first(((path, value) -> path.isNull().or(path.eq(value))));
        bindings.bind(root.createdAt)
                .as("date")
                .first(((path, value) -> path.between(value, value.plusDays(1).minusSeconds(1))));
    }

    boolean existsByCreatedAtAndStoreId(LocalDateTime requestTime, Long storeId);

    boolean existsByCreatedByAndStoreId(String username, Long storeId);

    Optional<Booking> findByCreatedByAndStoreId(String username, Long storeId);

    void deleteAllByCreatedAtBefore(LocalDateTime now);
}
