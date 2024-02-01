package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  private String name;

  /** possible reservation start time */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalTime startTime;

  /** possible reservation last time */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalTime lastTime;

  /**
   * reservation interval time(minute) if intervalTime is 30 minutes, you can make possible
   * reservation time like 12:00(startTime), 12:30, 13:00, ..., 18:00(lastTime)
   */
  private Integer intervalTime;

  @Enumerated(EnumType.STRING)
  private StoreType storeType;

  // TODO: add average rating and total review count fields -> update when write/update/delete
  // review

  @OneToOne
  @JoinColumn(name = "memberId")
  private Member member;

  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "store")
  private Set<Booking> bookings = new LinkedHashSet<>();

  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "store")
  private Set<Review> reviews = new LinkedHashSet<>();

  public Store(
      String name,
      LocalTime startTime,
      LocalTime lastTime,
      Integer intervalTime,
      StoreType storeType) {
    this.name = name;
    this.startTime = startTime;
    this.lastTime = lastTime;
    this.intervalTime = intervalTime;
    this.storeType = storeType;
  }

  public static Store of(
      String name,
      LocalTime startTime,
      LocalTime lastTime,
      Integer intervalTime,
      StoreType storeType) {
    return new Store(name, startTime, lastTime, intervalTime, storeType);
  }
  // TODO: implement isNew
}
