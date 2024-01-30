package org.zerobase.reservestoreapi.dto.request;

import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;

//TODO: validation
public record SignUpRequest(
        String username,
        String password,
        String nickname,
        MemberRole memberRole,
        String zipcode,
        String street,
        String detail,
        String phone
) {
    public Member toMemberEntity() {
        return Member.ofMember(
                username,
                password,
                nickname,
                memberRole,
                Address.of(zipcode, street, detail),
                phone
        );
    }

    public Member toStoreMemberEntity(Store store) {
        return Member.ofStore(
                username,
                password,
                nickname,
                memberRole,
                Address.of(zipcode, street, detail),
                phone,
                store
        );
    }
}
