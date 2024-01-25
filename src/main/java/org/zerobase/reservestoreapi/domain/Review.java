package org.zerobase.reservestoreapi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.zerobase.reservestoreapi.domain.auditing.AuditingFields;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String content;
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;
    //TODO: implement isNew
}
