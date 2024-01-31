package org.zerobase.reservestoreapi.domain.auditing;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
public abstract class AuditingAllFields extends AuditingCreatedFields {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @LastModifiedDate
  @Column(nullable = false)
  protected LocalDateTime modifiedAt;

  @LastModifiedBy
  @Column(nullable = false)
  protected String modifiedBy;
}
