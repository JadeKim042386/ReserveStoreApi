package org.zerobase.reservestoreapi.repository.custom;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Booking;

import java.time.LocalDateTime;

public interface CustomBookingRepository {
    Page<Booking> findAllByStoreId(Long storeId, Predicate predicate, Pageable pageable);

    /** Check exists booking had time or username for specific store */
    boolean existsCreateByStoreId(LocalDateTime time, String username, Long storeId);
}
