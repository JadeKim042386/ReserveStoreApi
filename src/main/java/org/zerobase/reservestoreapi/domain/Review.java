package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerobase.reservestoreapi.domain.auditing.AuditingAllFields;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review extends AuditingAllFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String content;
    //TODO: declare specific range (0~5)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    public Review(String content, Integer rating) {
        this.content = content;
        this.rating = rating;
    }

    public static Review of(String content, Integer rating) {
        return new Review(content, rating);
    }
    //TODO: implement isNew
}
