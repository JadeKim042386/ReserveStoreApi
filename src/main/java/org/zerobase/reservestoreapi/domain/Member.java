package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerobase.reservestoreapi.domain.constants.Address;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    public Member(String username, String password, String nickname, Address address, String phone) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
    }

    public static Member of(String username, String password, String nickname, Address address, String phone) {
        return new Member(username, password, nickname, address, phone);
    }

    //TODO: implement isNew
}
