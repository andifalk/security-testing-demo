package com.example.securitytesting.service;

import com.example.securitytesting.api.User;
import com.example.securitytesting.common.Gender;
import com.example.securitytesting.domain.CustomUserEntityRepository;
import com.example.securitytesting.domain.UserEntity;
import com.example.securitytesting.domain.UserEntityRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserService {

    private final UserEntityRepository userEntityRepository;

    private final CustomUserEntityRepository customUserEntityRepository;

    public UserService(UserEntityRepository userEntityRepository, CustomUserEntityRepository customUserEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.customUserEntityRepository = customUserEntityRepository;
    }

    public Optional<User> findOneByUserName(String username) {
        return userEntityRepository.findOneByUsername(username).map(User::new);
    }

    public Optional<User> findInsecureByUserName(String username) {
        return customUserEntityRepository.insecureFindUserEntityByName(username).map(User::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    @Transactional
    public User registerUser(Gender gender,
                             String username, String password,
                             String firstName,
                             String lastName,
                             String aboutMe,
                             int age,
                             String email) {
        UserEntity entity = new UserEntity(new User(UUID.randomUUID(), gender, username, password,
                firstName, lastName, aboutMe, age, email, Set.of("USER")));
        return new User(userEntityRepository.save(entity));
    }

    @Transactional
    public void delete(UserEntity entity) {
        userEntityRepository.delete(entity);
    }
}
