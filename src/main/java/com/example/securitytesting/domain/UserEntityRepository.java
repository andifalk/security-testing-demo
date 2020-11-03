package com.example.securitytesting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

  @Query("select u from UserEntity u where u.username = :username")
  Optional<UserEntity> findOneByUsername(@Param("username") String username);
}
