package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Store;

import java.util.Set;
import java.util.stream.Collectors;

public record StoreWithReviewDto(
  StoreDto storeDto,
  Set<ReviewDto> reviews
) {
  public static StoreWithReviewDto fromEntity(Store store) {
    return new StoreWithReviewDto(
      StoreDto.fromEntity(store),
      store.getReviews().stream()
        .map(ReviewDto::fromEntity)
        .collect(Collectors.toUnmodifiableSet())
    );
  }
}
