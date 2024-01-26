package org.zerobase.reservestoreapi.domain.auditing;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
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
