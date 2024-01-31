package org.zerobase.reservestoreapi.domain.auditing;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingCreatedFields {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  protected LocalDateTime createdAt;

  @CreatedBy
  @Column(nullable = false, updatable = false)
  protected String createdBy;
}
