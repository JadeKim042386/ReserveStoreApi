package org.zerobase.reservestoreapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.Store;
import org.zerobase.reservestoreapi.domain.constants.Address;
import org.zerobase.reservestoreapi.domain.constants.MemberRole;
import org.zerobase.reservestoreapi.dto.validation.ValidEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest{
  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String nickname;
  @ValidEnum(enumClass = MemberRole.class)
  private String memberRole;
  @NotBlank
  @Size(min = 5, max = 5)
  private String zipcode;
  @NotBlank
  private String street;
  @NotBlank
  private String detail;
  @NotBlank
  @Size(min = 9, max = 11)
  private String phone;
  private PartnerSignUpRequest partnerInfo;
  
  public Member toMemberEntity() {
    return Member.ofMember(
      username,
      password,
      nickname,
      MemberRole.valueOf(memberRole),
      Address.of(zipcode, street, detail),
      phone
    );
  }

  public Member toStoreMemberEntity(Store store) {
    return Member.ofStore(
      username,
      password,
      nickname,
      MemberRole.valueOf(memberRole),
      Address.of(zipcode, street, detail),
      phone,
      store
    );
  }
}
