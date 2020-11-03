package com.example.securitytesting.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class CustomUserEntityRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Optional<UserEntity> insecureFindUserEntityByName(String username) {
    Query query = entityManager.createNativeQuery("select * from user_entity u where u.username = '" + username + "'", UserEntity.class);
    return Optional.ofNullable((UserEntity) query.getSingleResult());
  }

  public Optional<UserEntity> secureFindUserEntityByName(String username) {
    Query query = entityManager.createNativeQuery("select * from user_entity u where u.username = :username", UserEntity.class);
    query.setParameter("username", username);
    return Optional.ofNullable((UserEntity) query.getSingleResult());
  }

}
