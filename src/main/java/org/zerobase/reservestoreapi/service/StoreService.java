package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;

public interface StoreService {
    /**
     * Get all stores
     */
    Page<StoreDto> searchStores(Pageable pageable);

    Store saveStore(Store store);

    /**
     * Get store detail info by storeId
     */
    StoreWithReviewDto searchStore(Long storeId);

    /**
     * Check already exists store name
     */
    boolean isExistsStoreName(String storeName);
}
