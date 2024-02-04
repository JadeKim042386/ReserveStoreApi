package org.zerobase.reservestoreapi.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.repository.BookingRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class BookingScheduler {
    private final BookingRepository bookingRepository;

    /** Delete not visited bookings every midnight */
    @Scheduled(cron = "${scheduler.booking.delete-not-visited}")
    public void deleteNotVisited() {
        try {
            bookingRepository.deleteAllByCreatedAtBefore(LocalDateTime.now());
            log.info("successfully delete not visited booking");
        } catch (Exception e) {
            log.error("occurs error during delete not visited booking!! {}", e.getMessage());
        }
    }
}
