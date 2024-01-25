package org.zerobase.reservestoreapi.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.zerobase.reservestoreapi.domain.auditing.AuditingFields;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private Boolean approve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;
    //TODO: implement isNew
}
