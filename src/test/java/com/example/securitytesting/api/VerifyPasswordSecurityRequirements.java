package com.example.securitytesting.api;

import com.example.securitytesting.common.Gender;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
public class VerifyPasswordSecurityRequirements {

    static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("2.1.1 Verify that user set passwords are at least 12 characters in length")
    @Order(1)
    @Test
    void verifyPasswordsAreAtLeast12CharactersInLength() {
        User user = createUserWithPassword("Psswor12_d");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password must be 12 or more characters in length.");
    }

    @DisplayName("2.1.2 Verify that passwords 64 characters or longer are permitted but may" +
            "be no longer than 128 characters")
    @Order(2)
    @Test
    void verifyPasswordsOf64CharactersOrLongerArePermitted() {
        User user = createUserWithPassword("Puikwor12_dklujikoljhnmztg67hgn5643rgwthshnnabsh7687manajdhkdusiskslakak87" +
                "jksnsn_okuhj:juiklasjldfjdhjkhaljlafiuioaioakljlklhsah");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();

        user = createUserWithPassword("Puikwor12_dklujikoljhnmztg67hgn5643rgwthshnnabsh7687manajdhkdusiskslakak87" +
                "jksnsn_okuhj:juiklasjldfjdhjkhaljlafiuioaioakljlklhsahjshskdkdjdhdndhdhdudjdndmmdkdldidjdjdzhdujlsj" +
                "kjsaldasioioeuoueqiouieoqrkjlkjklajklj");
        violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password must be no more than 128 characters in length.");
    }

    @DisplayName("2.1.3 Verify that password truncation is not performed")
    @Order(3)
    @Test
    void verifyPasswordTruncationIsNotPerformed() {
        String password = "P12_dklujikoljhnmztg67hgn5643rgwthshnnabsh7687manajdhkdusiskslakak87" +
                "jksnsn_okuhj:juiklasjldfjdhjkhaljlafiuioaioakljlklhsah";
        User user = createUserWithPassword(password);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @DisplayName("2.1.4 Verify that any printable Unicode character is permitted")
    @Order(4)
    @Test
    void verifyAnyPrintableUnicodeCharacterIsPermitted() {
        String emoji = "\\ud83d\\udc3b";
        String password = "Pthzjuk12_d!\"§$%&/()=?!_-:.;,öäü@€";
        User user = createUserWithPassword(password);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @DisplayName("2.1.7 Verify that passwords are checked against a set of breached passwords")
    @Order(7)
    @ParameterizedTest
    @ValueSource(strings = {"1234567Password", "password_123456", "passwort_123456", "quertz_123456", "quertz_password"})
    void verifyPasswordsAreCheckedAgainstBreachedPasswords(String password) {
        User user = createUserWithPassword(password);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains(
                "Password contains the dictionary word");
    }

    @DisplayName("2.1.9 Verify that there are no password composition rules limiting the type" +
            "of characters permitted. There should be no requirement for upper or" +
            "lower case or numbers or special characters")
    @Order(8)
    @ParameterizedTest
    @ValueSource(strings = {"hajanamanahaolakaiajkal", "!$/()(&%=)(%$§$$&/&%&((/", "NHJMNGGGHJKUZTTIKKKLLO", "18278625363898653"})
    void verifyThereIsNoPasswordCompositionRulesLimiting(String password) {
        User user = createUserWithPassword(password);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
        assertThat(user.getPassword()).isEqualTo(password);
    }

    private User createUserWithPassword(String password) {
        return
                new User(
                        UUID.randomUUID(),
                        Gender.MALE,
                        "myuser",
                        password,
                        "firstName",
                        "lastName",
                        "aboutMe",
                        20,
                        "test@example.com",
                        Set.of("USER"));
    }
}
