package com.example.securitytesting.domain;

import com.example.securitytesting.common.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class SqlInjectionTest {

  @Autowired private CustomUserEntityRepository customUserEntityRepository;

  @Autowired private UserEntityRepository userEntityRepository;

  @BeforeEach
  void init() {
    UserEntity userEntity = new UserEntity();
    userEntity.setAboutMe("123");
    userEntity.setAge(20);
    userEntity.setEmail("test@example.com");
    userEntity.setFirstName("Hans");
    userEntity.setLastName("Mustermann");
    userEntity.setGender(Gender.MALE);
    userEntity.setIdentifier(UUID.randomUUID());
    userEntity.setPassword("secret123456789");
    userEntity.setUsername("test");
    userEntity.setRoles(Set.of("USER"));
    userEntityRepository.save(userEntity);

    userEntity = new UserEntity();
    userEntity.setAboutMe("123");
    userEntity.setAge(20);
    userEntity.setEmail("test1@example.com");
    userEntity.setFirstName("Max");
    userEntity.setLastName("Meier");
    userEntity.setGender(Gender.MALE);
    userEntity.setIdentifier(UUID.randomUUID());
    userEntity.setPassword("secret123456789");
    userEntity.setUsername("test1");
    userEntity.setRoles(Set.of("USER"));
    userEntityRepository.save(userEntity);
  }

  @Test
  void sqlInjection() {
    assertThatExceptionOfType(IncorrectResultSizeDataAccessException.class)
        .isThrownBy(
            () -> customUserEntityRepository.insecureFindUserEntityByName("invalid' or 1=1--"));
  }

  @Test
  void noSqlInjection() {
    assertThatExceptionOfType(EmptyResultDataAccessException.class)
        .isThrownBy(
            () -> customUserEntityRepository.secureFindUserEntityByName("invalid' or 1=1--"));
  }
}
