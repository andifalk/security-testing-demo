package com.example.securitytesting.api;

import com.example.securitytesting.common.Gender;
import com.example.securitytesting.domain.UserEntity;
import com.example.securitytesting.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {

  @JsonProperty("id")
  @NotNull
  private UUID identifier;

  @NotNull private Gender gender;

  @NotBlank(message = "User name cannot be blank")
  @Pattern(regexp = "^[A-Za-z0-9]{3,30}", message = "User name must only contain characters and numbers and must be between 3 and 30 characters")
  private String username;

  @JsonIgnore
  @ValidPassword(message = "Password does not comply with password policy")
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

  @NotNull private Set<String> roles = new HashSet<>();

  public User() {}

  public User(UserEntity userEntity) {
    this(
        userEntity.getIdentifier(),
        userEntity.getGender(),
        userEntity.getUsername(),
        userEntity.getPassword(),
        userEntity.getFirstName(),
        userEntity.getLastName(),
        userEntity.getAboutMe(),
        userEntity.getAge(),
        userEntity.getEmail(),
        userEntity.getRoles());
  }

  public User(
      UUID identifier,
      Gender gender,
      String username,
      String password,
      String firstName,
      String lastName,
      String aboutMe,
      int age,
      String email,
      Set<String> roles) {
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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String userName) {
    this.username = userName;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
