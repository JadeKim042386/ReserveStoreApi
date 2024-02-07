package org.zerobase.reservestoreapi.domain;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreReviewInfo implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Setter
    @Column(
            nullable = false,
            columnDefinition =
                    "float constraint average_rating_range check (average_rating between 0 and 5)")
    private Float averageRating;

    @Column(nullable = false)
    @Setter private Integer reviewCount;

    @OneToOne(mappedBy = "storeReviewInfo")
    private Store store;

    public StoreReviewInfo(Float averageRating, Integer reviewCount) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public static StoreReviewInfo of(Float averageRating, Integer reviewCount) {
        return new StoreReviewInfo(averageRating, reviewCount);
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }
}
