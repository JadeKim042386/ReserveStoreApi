package org.zerobase.reservestoreapi.service;

import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;

import java.util.List;

public interface StoreService {
    /**
     * Get all stores
     */
    List<StoreDto> searchStores();

    /**
     * Get store detail info by storeId
     */
    StoreWithReviewDto searchStore(Long storeId);
}
