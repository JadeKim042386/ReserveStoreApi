package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import java.util.Set;

public record StoreDto(
        String name,
        Set<String> possibleBookingTimes,
        StoreType storeType,
        Address address,
        String phone
) {
}
