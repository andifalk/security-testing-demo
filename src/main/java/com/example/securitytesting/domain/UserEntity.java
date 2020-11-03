package com.example.securitytesting.domain;

import com.example.securitytesting.api.User;
import com.example.securitytesting.common.Gender;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class UserEntity extends AbstractPersistable<Long> {

  @NotNull private UUID identifier;

  @Enumerated(EnumType.STRING)
  @NotNull private Gender gender;

  @NotBlank(message = "User name cannot be blank")
  @Size(min = 3, max = 30, message = "User name must be between 3 and 30 characters")
  private String username;

  @NotBlank(message = "Password cannot be blank")
  @Size(min = 10, max = 100, message = "Password must be between 10 and 100 characters")
  private String password;

  @NotBlank(message = "First name cannot be blank")
  @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
  private String lastName;

  @NotNull
  @Pattern(regexp = "^[A-Za-z0-9-!?/. ]{1,500}$", message = "About me contains invalid character(s)")
  private String aboutMe;

  @Min(value = 18, message = "Age should not be less than 18")
  @Max(value = 150, message = "Age should not be greater than 150")
  private int age;

  @Email(message = "Email should be valid")
  private String email;

  @NotNull
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> roles = new HashSet<>();

  public UserEntity() {
  }

  public UserEntity(User user) {
    this(user.getIdentifier(), user.getGender(), user.getUsername(), user.getPassword(), user.getFirstName(),
            user.getLastName(), user.getAboutMe(), user.getAge(), user.getEmail(), user.getRoles());
  }

  public UserEntity(UUID identifier, Gender gender, String username, String password,
                    String firstName, String lastName,
                    String aboutMe, int age, String email, Set<String> roles) {
    this.identifier = identifier;
    this.gender = gender;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.aboutMe = aboutMe;
    this.age = age;
    this.email = email;
    this.roles = roles;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "UserEntity{"
        + "gender="
        + gender
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", aboutMe='"
        + aboutMe
        + '\''
        + ", age="
        + age
        + ", email='"
        + email
        + '\''
        + "} "
        + super.toString();
  }
}
