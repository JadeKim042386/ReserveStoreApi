package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.zerobase.reservestoreapi.domain.auditing.AuditingCreatedFields;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Booking extends AuditingCreatedFields implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Boolean approve;

    //TODO: add phone

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    public Booking(Boolean approve) {
        this.approve = approve;
    }

    public static Booking of(Boolean approve) {
        return new Booking(approve);
    }

    public void approval() {
        this.approve = true;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }
}
