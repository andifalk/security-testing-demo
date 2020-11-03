package com.example.securitytesting.api;

import com.example.securitytesting.common.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UserInputValidationTest {

  static Validator validator;

  @BeforeAll
  static void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @MethodSource("validUserNamesProvider")
  @ParameterizedTest
  void verifyValidUsernames(String username) {

    User user = createUser(username);

    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations).isEmpty();
  }

  @MethodSource("invalidUserNamesProvider")
  @ParameterizedTest
  void verifyInvalidUsernames(String username) {

    User user = createUser(username);

    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertThat(violations).isNotEmpty();
  }

  private User createUser(String username) {
    return new User(
            UUID.randomUUID(),
            Gender.MALE,
            username,
            "Puzhjujrd_12_Tkilkjt",
            "firstName",
            "lastName",
            "aboutMe",
            20,
            "test@example.com",
            Set.of("USER"));
  }

  static Stream<String> validUserNamesProvider() {
    return Stream.of("abc", "123", "123456789012345678901234567890", "ab23cd56jh78");
  }

  static Stream<String> invalidUserNamesProvider() {
    return Stream.of("  ", null, "12", "aa", "1 2", "1 2 3", "1234567890123456789012345678901", "<javascript>alert('xss')</javascript>", "or 1=1 --", "$%/_12345");
  }
}
