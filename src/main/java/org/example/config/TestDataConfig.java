package org.example.config;

import jakarta.annotation.PostConstruct;
import org.example.model.entity.Role;
import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Profile("develop")
public class TestDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean init;

    @Autowired
    public TestDataConfig(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${spring.jpa.hibernate.ddl-auto}") String init) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.init = init.equals("create");
    }

    @PostConstruct
    private void initTestUsers() {
        if (!init)
            return;
        createUser("user", "user", Set.of(Role.USER));
        createUser("employee", "employee", Set.of(Role.EMPLOYEE));
        createUser("admin", "admin", Set.of(Role.ADMIN));
        createUser("superuser", "superuser", Set.of(Role.USER, Role.EMPLOYEE, Role.ADMIN));
    }

    private void createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
