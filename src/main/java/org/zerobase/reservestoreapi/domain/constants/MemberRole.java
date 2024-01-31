package org.zerobase.reservestoreapi.domain.constants;

import lombok.Getter;

@Getter
public enum MemberRole {
  MEMBER("ROLE_MEMBER"),
  ADMIN("ROLE_ADMIN"),
  STORE("ROLE_STORE"),
  ANONYMOUS("ROLE_ANONYMOUS");

  private final String name;

  MemberRole(String name) {
    this.name = name;
  }
}
