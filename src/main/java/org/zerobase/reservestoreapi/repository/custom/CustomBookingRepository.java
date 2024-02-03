package org.zerobase.reservestoreapi.repository.custom;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Booking;

public interface CustomBookingRepository {
    Page<Booking> findAllByStoreId(Long storeId, Predicate predicate, Pageable pageable);
}
