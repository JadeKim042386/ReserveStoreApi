package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(indexes = {@Index(name = "store_name_idx", columnList = "name", unique = true)})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Store implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    /** possible reservation start time */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime startTime;

    /** possible reservation last time */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime lastTime;

    /**
     * reservation interval time(minute) if intervalTime is 30 minutes, you can make possible
     * reservation time like 12:00(startTime), 12:30, 13:00, ..., 18:00(lastTime)
     */
    @Column(nullable = false)
    private Integer intervalTime;

    @Column(nullable = false)
    private Float distance; // Meter

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "storeReviewInfoId")
    private StoreReviewInfo storeReviewInfo;

    @OneToOne(mappedBy = "store")
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
            Float distance,
            StoreType storeType) {
        this.name = name;
        this.startTime = startTime;
        this.lastTime = lastTime;
        this.intervalTime = intervalTime;
        this.distance = distance;
        this.storeType = storeType;
        this.storeReviewInfo = StoreReviewInfo.of(0f, 0);
    }

    public static Store of(
            String name,
            LocalTime startTime,
            LocalTime lastTime,
            Integer intervalTime,
            Float distance,
            StoreType storeType) {
        return new Store(name, startTime, lastTime, intervalTime, distance, storeType);
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }
}
