package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;

import java.util.List;

public interface StoreService {
    /**
     * Get all stores
     */
    List<StoreDto> searchStores();

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
