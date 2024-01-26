package org.zerobase.reservestoreapi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerobase.reservestoreapi.domain.constants.StoreType;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    /**
     * possible reservation start time
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime startTime;
    /**
     * possible reservation last time
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalTime lastTime;
    /**
     * reservation interval time(minute)
     * if intervalTime is 30 minutes, you can make possible reservation time like 12:00(startTime), 12:30, 13:00, ..., 18:00(lastTime)
     */
    private Integer intervalTime;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "store")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "store")
    private Set<Review> reviews = new LinkedHashSet<>();
    //TODO: implement isNew
}
