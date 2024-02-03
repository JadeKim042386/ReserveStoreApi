package org.zerobase.reservestoreapi.repository.custom;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.zerobase.reservestoreapi.domain.Booking;
import org.zerobase.reservestoreapi.domain.QBooking;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomBookingRepositoryImpl implements CustomBookingRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Booking> findAllByStoreId(Long storeId, Predicate predicate, Pageable pageable) {
        List<Booking> bookings =
                jpaQueryFactory
                        .selectFrom(QBooking.booking)
                        .where(predicate)
                        .where(QBooking.booking.store.id.eq(storeId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return new PageImpl<>(bookings, pageable, bookings.size());
    }
}
