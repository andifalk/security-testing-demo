package com.example.securitytesting;

import com.example.securitytesting.common.Gender;
import com.example.securitytesting.domain.UserEntity;
import com.example.securitytesting.domain.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class DataInitializer implements CommandLineRunner {

    private final UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        userEntityRepository.save(new UserEntity(UUID.randomUUID(), Gender.MALE, "mmuster",
                passwordEncoder.encode("muster_password_2020"), "Max",
                "Muster", "About Me", 30, "test@example.com", Set.of("USER", "ADMIN")));
    }
}
