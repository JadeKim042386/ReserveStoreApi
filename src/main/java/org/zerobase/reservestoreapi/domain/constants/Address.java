package org.zerobase.reservestoreapi.domain.constants;

import jakarta.persistence.Embeddable;
import lombok.Getter;


@Getter
@Embeddable
public class Address {
    private String zipcode;
    private String street;
    private String detail;
}
