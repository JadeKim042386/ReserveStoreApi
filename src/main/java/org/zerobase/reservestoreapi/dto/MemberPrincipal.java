package org.zerobase.reservestoreapi.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerobase.reservestoreapi.domain.Member;
import org.zerobase.reservestoreapi.domain.constants.Address;

import java.util.Collection;
import java.util.Set;

public record MemberPrincipal(
  Long id,
  String username,
  String password,
  String nickname,
  Address address,
  String phone,
  Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

  public static MemberPrincipal fromEntity(Member member) {
    return new MemberPrincipal(
      member.getId(),
      member.getUsername(),
      member.getPassword(),
      member.getNickname(),
      member.getAddress(),
      member.getPhone(),
      Set.of(new SimpleGrantedAuthority(member.getMemberRole().getName()))
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
