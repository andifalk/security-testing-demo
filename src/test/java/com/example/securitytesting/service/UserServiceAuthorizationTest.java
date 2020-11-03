package com.example.securitytesting.service;

import com.example.securitytesting.domain.CustomUserEntityRepository;
import com.example.securitytesting.domain.UserEntity;
import com.example.securitytesting.domain.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceAuthorizationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private CustomUserEntityRepository customUserEntityRepository;

    @WithMockUser(roles = "USER")
    @Test
    void findOneByUserNameIsAuthorized() {
        when(userEntityRepository.findOneByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(new UserEntity()));
        assertThat(userService.findOneByUserName("test")).isNotEmpty();
    }

    @WithMockUser(roles = "DUMMY")
    @Test
    void findOneByUserNameIsUnAuthorized() {
        assertThatThrownBy(() -> userService.findOneByUserName("test")).isInstanceOf(AccessDeniedException.class);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void findAllIsAuthorized() {
        when(userEntityRepository.findAll()).thenReturn(List.of(new UserEntity()));
        assertThat(userService.findAll()).isNotEmpty();
    }

    @WithMockUser(roles = "USER")
    @Test
    void findAllIsUnAuthorized() {
        assertThatThrownBy(() -> userService.findAll()).isInstanceOf(AccessDeniedException.class);
    }
}