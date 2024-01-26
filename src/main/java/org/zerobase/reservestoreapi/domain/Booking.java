package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerobase.reservestoreapi.domain.auditing.AuditingCreatedFields;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Booking extends AuditingCreatedFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private Boolean approve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    public Booking(Boolean approve) {
        this.approve = approve;
    }

    public static Booking of(Boolean approve) {
        return new Booking(approve);
    }

    //TODO: implement isNew
}
