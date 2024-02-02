package org.zerobase.reservestoreapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.StoreType;
import org.zerobase.reservestoreapi.dto.validation.ValidEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSignUpRequest {
    @NotBlank private String storeName;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime lastTime;

    @NotNull
    @Min(10)
    @Max(1440)
    private Integer intervalTime;

    @ValidEnum(enumClass = StoreType.class)
    private String storeType;

    public Store toStoreEntity() {
        return Store.of(storeName, startTime, lastTime, intervalTime, StoreType.valueOf(storeType));
    }
}
