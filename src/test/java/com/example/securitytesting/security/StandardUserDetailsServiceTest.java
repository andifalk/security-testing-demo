package com.example.securitytesting.security;

import com.example.securitytesting.api.User;
import com.example.securitytesting.service.UserService;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandardUserDetailsServiceTest {

  @Mock private UserService userService;

  @Mock private User user;

  @Test
  void verifyLogOutput() {
    when(userService.findOneByUserName(any())).thenReturn(Optional.of(user));

    String username = "testuser";
    String expectedInfoMessage = String.format("Load user for [%s]", username);

    LogCaptor<StandardUserDetailsService> logCaptor =
        LogCaptor.forClass(StandardUserDetailsService.class);

    UserDetailsService userDetailsService = new StandardUserDetailsService(userService);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    assertThat(userDetails).isNotNull();
    assertThat(logCaptor.getInfoLogs()).containsExactly(expectedInfoMessage);
  }
}
