package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.dto.constants.CacheKey;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.exception.StoreException;
import org.zerobase.reservestoreapi.exception.constant.ErrorCode;
import org.zerobase.reservestoreapi.repository.StoreRepository;
import org.zerobase.reservestoreapi.service.StoreService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    /**
     * fist page caching
     */
    @Cacheable(value = CacheKey.KEY_STORE, key = "#pageable.pageNumber", condition = "#pageable.pageNumber == 0")
    @Override
    public PagedResponse<StoreDto> searchStores(Pageable pageable) {
        return PagedResponse.of(storeRepository.findAll(pageable).map(StoreDto::fromEntity));
    }

    @Override
    public Store searchStore(Long storeId) {
        return storeRepository
                .findById(storeId)
                .orElseThrow(() -> new StoreException(ErrorCode.NOT_FOUND_ENTITY));
    }

    @CacheEvict(value = CacheKey.KEY_STORE, allEntries = true)
    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public StoreWithReviewDto searchStoreWithReviewDto(Long storeId) {
        return StoreWithReviewDto.fromEntity(searchStore(storeId));
    }

    @Override
    public boolean isExistsStoreName(String storeName) {
        return storeRepository.existsByName(storeName);
    }

    @Override
    public boolean isExistsStoreByUsername(Long storeId, String username) {
        return storeRepository.existsByIdAndMember_Username(storeId, username);
    }
}
