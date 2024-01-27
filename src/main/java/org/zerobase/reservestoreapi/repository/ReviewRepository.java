package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerobase.reservestoreapi.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT round(avg(r.rating), 1) FROM Review r GROUP BY r.store.id HAVING r.store.id = :storeId")
    Float getAverageRatingByStoreId(Long storeId);
}
