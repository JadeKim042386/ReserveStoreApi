package org.zerobase.reservestoreapi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.zerobase.reservestoreapi.domain.constants.Address;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String username;
    private String password;
    private String nickname;

    @Embedded
    private Address address;
    private String phone;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Store store;
    //TODO: implement isNew
}
