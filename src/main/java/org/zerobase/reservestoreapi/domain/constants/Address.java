package org.zerobase.reservestoreapi.domain.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Column(length = 10)
    private String zipcode;

    @Column(length = 100)
    private String street;

    @Column(length = 50)
    private String detail;

    public static Address of(String zipcode, String street, String detail) {
        return new Address(zipcode, street, detail);
    }
}
