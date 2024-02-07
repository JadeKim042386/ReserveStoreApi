package org.zerobase.reservestoreapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.reservestoreapi.domain.Review;
import org.zerobase.reservestoreapi.dto.ReviewDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByIdAndCreatedBy(Long reviewId, String username);

    boolean existsByStoreIdAndCreatedBy(Long storeId, String username);

    Page<Review> findAllByStoreId(Long storeId, Pageable pageable);
}
