package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;

public interface StoreService {
  /** Get all stores */
  Page<StoreDto> searchStores(Pageable pageable);

  /**
   * Get specific store
   */
  Store searchStore(Long storeId);

  Store saveStore(Store store);

  /** Get store detail DTO by storeId */
  StoreWithReviewDto searchStoreWithReviewDto(Long storeId);

  /** Check already exists store name */
  boolean isExistsStoreName(String storeName);
}
