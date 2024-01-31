package org.zerobase.reservestoreapi.domain.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
  private String zipcode;
  private String street;
  private String detail;

  public static Address of(String zipcode, String street, String detail) {
    return new Address(zipcode, street, detail);
  }
}
