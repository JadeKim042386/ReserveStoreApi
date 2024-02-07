package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.StoreWithReviewDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.service.ReviewService;
import org.zerobase.reservestoreapi.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreApi {
    private final StoreService storeService;

    /**
     * Retrieve all stores.
     * <pre>
     *     Example:
     *     api/v1/stores?sort=name,desc&sort=storeReviewInfo.averageRating,asc&sort=distance,asc
     * </pre>
     */
    @GetMapping
    public ResponseEntity<PagedResponse<StoreDto>> searchAllStores(
            @PageableDefault(sort = {"name", "storeReviewInfo.averageRating", "distance"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(storeService.searchStores(pageable));
    }

    /** Retrieve specific store */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDto> searchStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.searchStoreDto(storeId));
    }
}
