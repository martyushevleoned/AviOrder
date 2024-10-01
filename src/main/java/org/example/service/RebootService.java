package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.model.entity.Role;
import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class RebootService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean needToCreateTestUsers;

    public RebootService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${spring.jpa.hibernate.ddl-auto}") String createProperty) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.needToCreateTestUsers = Objects.equals(createProperty, "create");
    }

    @PostConstruct
    private void init() {

        if (!needToCreateTestUsers)
            return;

        createUser("admin", "admin", Set.of(Role.ADMIN));
        createUser("worker", "worker", Set.of(Role.WORKER, Role.USER));
        createUser("user", "user", Set.of(Role.USER));
    }

    private void createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
