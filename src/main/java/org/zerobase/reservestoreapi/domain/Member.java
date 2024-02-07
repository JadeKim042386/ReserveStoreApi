package org.zerobase.reservestoreapi.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// TODO: username, nickname, phone is unique
public class Member implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String username;
    private String password;
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @Embedded private Address address;
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "storeId")
    private Store store;

    public Member(
            String username,
            String password,
            String nickname,
            MemberRole memberRole,
            Address address,
            String phone,
            Store store) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = memberRole;
        this.address = address;
        this.phone = phone;
        this.store = store;
    }

    public static Member ofStore(
            String username,
            String password,
            String nickname,
            MemberRole memberRole,
            Address address,
            String phone,
            Store store) {
        return new Member(username, password, nickname, memberRole, address, phone, store);
    }

    public static Member ofMember(
            String username,
            String password,
            String nickname,
            MemberRole memberRole,
            Address address,
            String phone) {
        return new Member(username, password, nickname, memberRole, address, phone, null);
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.id);
    }
}
