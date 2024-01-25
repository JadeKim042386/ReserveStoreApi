package org.zerobase.reservestoreapi.domain.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@ToString
@MappedSuperclass
public abstract class AuditingFields extends AuditingAt {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    protected String modifiedBy;
}
