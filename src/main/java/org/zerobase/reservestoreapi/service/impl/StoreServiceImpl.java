package org.zerobase.reservestoreapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.repository.StoreRepository;
import org.zerobase.reservestoreapi.service.StoreService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public List<StoreDto> searchStores() {
        //TODO: pagination
        return storeRepository.findAll().stream()
                .map(StoreDto::fromEntity).toList();
    }

    @Override
    public StoreWithReviewDto searchStore(Long storeId) {
        //TODO: handling exception
        return storeRepository.findById(storeId)
                .map(StoreWithReviewDto::fromEntity)
                .orElseThrow(EntityNotFoundException::new);
    }
}
