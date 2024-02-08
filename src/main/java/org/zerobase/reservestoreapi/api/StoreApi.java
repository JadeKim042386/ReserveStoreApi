package org.zerobase.reservestoreapi.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerobase.reservestoreapi.dto.StoreDto;
import org.zerobase.reservestoreapi.dto.response.PagedResponse;
import org.zerobase.reservestoreapi.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreApi {
    private final StoreService storeService;

    /**
     * Retrieve all stores.
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores?sort=name,desc&sort=storeReviewInfo.averageRating,asc&sort=distance,asc
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "content": List<StoreDto>,
     *     "number": 0,
     *     "size": 10,
     *     "sort": [
     *         {
     *             "direction": "DESC",
     *             "property": "name",
     *             "ignoreCase": false,
     *             "nullHandling": "NATIVE",
     *             "descending": true,
     *             "ascending": false
     *         },
     *         {
     *             "direction": "ASC",
     *             "property": "storeReviewInfo.averageRating",
     *             "ignoreCase": false,
     *             "nullHandling": "NATIVE",
     *             "descending": false,
     *             "ascending": true
     *         },
     *         {
     *             "direction": "ASC",
     *             "property": "distance",
     *             "ignoreCase": false,
     *             "nullHandling": "NATIVE",
     *             "descending": false,
     *             "ascending": true
     *         }
     *     ],
     *     "totalElements": 0,
     *     "totalPages": 0,
     *     "first": true,
     *     "last": true
     * }
     * </pre>
     */
    @Operation(summary = "Retrieve all stores")
    @GetMapping
    public ResponseEntity<PagedResponse<StoreDto>> searchAllStores(
            @PageableDefault(
                            sort = {"name", "storeReviewInfo.averageRating", "distance"},
                            direction = Sort.Direction.ASC)
                    Pageable pageable) {
        return ResponseEntity.ok(storeService.searchStores(pageable));
    }

    /**
     * Retrieve specific store
     *
     * <pre>
     * Request Example:
     * - /api/v1/stores/1
     * </pre>
     *
     * <pre>
     * Response Example:
     * {
     *     "name": "store",
     *     "possibleBookingTimes": [
     *         "09:00",
     *         "09:30",
     *         "10:00",
     *         "10:30",
     *         "11:00",
     *         "11:30",
     *         "12:00",
     *         "12:30",
     *         "13:00",
     *         "13:30",
     *         "14:00",
     *         "14:30",
     *         "15:00",
     *         "15:30",
     *         "16:00",
     *         "16:30",
     *         "17:00",
     *         "17:30"
     *     ],
     *     "storeType": "BAR",
     *     "address": {
     *         "zipcode": "11111",
     *         "street": "street",
     *         "detail": "detail"
     *      },
     *     "phone": "01011111111",
     *     "distance": 11.1
     * }
     * </pre>
     */
    @Operation(summary = "Retrieve specific store")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDto> searchStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.searchStoreDto(storeId));
    }
}
