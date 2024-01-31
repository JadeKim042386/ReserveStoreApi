package org.zerobase.reservestoreapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerobase.reservestoreapi.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreApi {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<?> searchAllStores(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                storeService.searchStores(pageable)
        );
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<?> searchStore(
            @PathVariable Long storeId
    ) {
        return ResponseEntity.ok(
                storeService.searchStore(storeId)
        );
    }
}
