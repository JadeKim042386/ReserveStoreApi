package org.zerobase.reservestoreapi.domain;

import lombok.*;
import org.springframework.data.domain.Persistable;
import org.zerobase.reservestoreapi.domain.auditing.AuditingAllFields;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(
        indexes = {
            @Index(name = "id_and_created_by", columnList = "id, createdBy"),
            @Index(name = "store_id_and_created_by", columnList = "storeId, createdBy"),
            @Index(name = "store_id_idx", columnList = "storeId")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review extends AuditingAllFields implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @Setter
    private String content;

    @Column(
            nullable = false,
            columnDefinition = "int constraint rating_range check (rating between 0 and 5)")
    @Setter
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    public Review(String content, Integer rating, Store store) {
        this.content = content;
        this.rating = rating;
        this.store = store;
    }

    public static Review of(String content, Integer rating, Store store) {
        return new Review(content, rating, store);
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }
}
