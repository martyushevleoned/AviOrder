package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.model.entity.Role;
import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

// TODO Разобраться с миграциями
@Service
public class RebootService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RebootService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void createTestUsers() {
        createUser("123","123", Set.of(Role.USER));
        createUser("user","user", Set.of(Role.USER));
    }

    private void createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
