package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;

public interface StoreService {
    /** Get all stores */
    PagedResponse<StoreDto> searchStores(Pageable pageable);

    /** Get specific store */
    Store searchStore(Long storeId);

    Store saveStore(Store store);

    /** Get store detail DTO by storeId */
    StoreDto searchStoreDto(Long storeId);

    /** Check already exists store name */
    boolean isExistsStoreName(String storeName);

    /** Check exists store member had specific storeId */
    boolean isExistsStoreByUsername(Long storeId, String username);
}
