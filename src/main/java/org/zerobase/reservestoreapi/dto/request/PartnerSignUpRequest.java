package org.zerobase.reservestoreapi.dto.request;

import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import java.time.LocalTime;

public record PartnerSignUpRequest(
        SignUpRequest signUpRequest,
        String storeName,
        LocalTime startTime,
        LocalTime lastTime,
        Integer intervalTime,
        StoreType storeType
) {
    public Store toStoreEntity() {
        return Store.of(
                storeName,
                startTime,
                lastTime,
                intervalTime,
                storeType
        );
    }
}
