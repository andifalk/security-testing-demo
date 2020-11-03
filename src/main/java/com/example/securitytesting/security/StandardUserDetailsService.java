package com.example.securitytesting.security;

import com.example.securitytesting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Qualifier("MyUserDetails")
@Service
public class StandardUserDetailsService implements UserDetailsService  {

  private static final Logger LOG = LoggerFactory.getLogger(StandardUserDetailsService.class);

  private final UserService userService;

  public StandardUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LOG.info("Load user for [{}]", username);
    return userService.findOneByUserName(username).map(AuthenticatedUser::new).orElseThrow(() -> new UsernameNotFoundException("No user"));
  }
}
