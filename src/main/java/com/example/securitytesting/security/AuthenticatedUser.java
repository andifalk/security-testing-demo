package com.example.securitytesting.security;

import com.example.securitytesting.api.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthenticatedUser extends User implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList(getRoles().toArray(new String[0]));
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

  public AuthenticatedUser() {
    super();
  }

  public AuthenticatedUser(User user) {
    super(
        user.getIdentifier(),
        user.getGender(),
        user.getUsername(),
        user.getPassword(),
        user.getFirstName(),
        user.getLastName(),
        user.getAboutMe(),
        user.getAge(),
        user.getEmail(),
        user.getRoles());
  }
}
