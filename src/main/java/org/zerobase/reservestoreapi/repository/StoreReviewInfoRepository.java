package org.zerobase.reservestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerobase.reservestoreapi.domain.StoreReviewInfo;

public interface StoreReviewInfoRepository extends JpaRepository<StoreReviewInfo, Long> {
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE store_review_info sri," +
                        " (SELECT round(avg(r.rating), 1) average_rating, count(*) reivew_count"
                        + " FROM review r"
                        + " WHERE r.store_id = 1"
                        + " GROUP BY r.store_id) info"
                    + " SET sri.average_rating = info.average_rating,"
                        + " sri.review_count = info.reivew_count"
                    + " WHERE sri.id = (SELECT s.store_review_info_id FROM store s WHERE s.id = ?1)")
    void updateStoreReviewInfoByStoreId(Long storeId);
}
