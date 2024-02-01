package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.repository.StoreRepository;
import org.zerobase.reservestoreapi.service.StoreService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
  private final StoreRepository storeRepository;

  @Override
  public Page<StoreDto> searchStores(Pageable pageable) {
    return storeRepository.findAll(pageable).map(StoreDto::fromEntity);
  }

  @Override
  public Store searchStore(Long storeId) {
    return storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Store saveStore(Store store) {
    return storeRepository.save(store);
  }

  @Override
  public StoreWithReviewDto searchStoreWithReviewDto(Long storeId) {
    // TODO: handling exception
    return StoreWithReviewDto.fromEntity(searchStore(storeId));
  }

  @Override
  public boolean isExistsStoreName(String storeName) {
    return storeRepository.existsByName(storeName);
  }
}
