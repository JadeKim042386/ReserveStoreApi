package org.zerobase.reservestoreapi.domain.constants;

import lombok.Getter;

import javax.persistence.Embeddable;


@Getter
@Embeddable
public class Address {
    private String zipcode;
    private String street;
    private String detail;
}
