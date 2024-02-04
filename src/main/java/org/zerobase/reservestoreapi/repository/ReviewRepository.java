package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByIdAndCreatedBy(Long reviewId, String username);

    boolean existsByStoreIdAndCreatedBy(Long storeId, String username);
}
