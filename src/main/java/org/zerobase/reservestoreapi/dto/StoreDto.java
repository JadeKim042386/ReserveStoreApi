package org.zerobase.reservestoreapi.dto;

import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public record StoreDto(
    String name,
    Set<String> possibleBookingTimes,
    StoreType storeType,
    Address address,
    String phone) {
  public static StoreDto fromEntity(Store store) {
    return new StoreDto(
        store.getName(),
        generateBookingTimes(store.getStartTime(), store.getLastTime(), store.getIntervalTime()),
        store.getStoreType(),
        store.getMember().getAddress(),
        store.getMember().getPhone());
  }

  private static Set<String> generateBookingTimes(
      LocalTime startTime, LocalTime lastTime, Integer intervalTime) {
    Set<String> times = new LinkedHashSet<>();
    for (LocalTime time = startTime;
        time.isBefore(lastTime);
        time = time.plusMinutes(intervalTime)) {
      times.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
    return Collections.unmodifiableSet(times);
  }
}
