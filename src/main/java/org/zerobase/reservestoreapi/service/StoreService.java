package org.zerobase.reservestoreapi.service;

import org.springframework.data.domain.Pageable;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;

public interface StoreService {
    /** Retrieve stores */
    PagedResponse<StoreDto> searchStores(Pageable pageable);

    /** Retrieve specific store */
    Store searchStore(Long storeId);

    /** Retrieve store detail DTO by storeId */
    StoreDto searchStoreDto(Long storeId);

    /** Check already exists store had storeName */
    boolean isExistsStoreName(String storeName);
}
