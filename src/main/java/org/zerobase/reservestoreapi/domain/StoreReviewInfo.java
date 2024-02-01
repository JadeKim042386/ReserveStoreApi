package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreReviewInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(
      columnDefinition =
          "float constraint average_rating_range check (average_rating between 0 and 5)")
  private Float averageRating;

  private Integer reviewCount;

  @OneToOne
  @JoinColumn(name = "storeId")
  private Store store;

  public StoreReviewInfo(Float averageRating, Integer reviewCount) {
    this.averageRating = averageRating;
    this.reviewCount = reviewCount;
  }

  public static StoreReviewInfo of(Float averageRating, Integer reviewCount) {
    return new StoreReviewInfo(averageRating, reviewCount);
  }
  // TODO: implement isNew
}
