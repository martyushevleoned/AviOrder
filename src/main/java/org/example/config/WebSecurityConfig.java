package org.example.config;

import org.example.model.constant.Page;
import org.example.model.constant.Resource;
import org.example.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Устанавливает права доступа основываясь на его {@link Role}
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests

                        // permit all
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.HOME.getUrl()).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.REGISTRATION.getUrl()).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                Page.REGISTRATION.getUrl()).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                Resource.STATIC.getAnyParamUrl()).permitAll()

                        // user
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.VIEW_ORDER.getAnyParamUrl()).hasAuthority(
                                Role.USER.getAuthority())
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.ACCOUNT.getUrl()).hasAuthority(
                                Role.USER.getAuthority())
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.EDIT_ORDER.getAnyParamUrl()).hasAuthority(
                                Role.USER.getAuthority())
                        .requestMatchers(
                                HttpMethod.GET,
                                Resource.ORDER.getUrl()).hasAuthority(
                                Role.USER.getAuthority())
                        .requestMatchers(
                                HttpMethod.PUT,
                                Resource.SAVE_ORDER.getUrl()).hasAuthority(
                                Role.USER.getAuthority())
                        .requestMatchers(
                                HttpMethod.POST,
                                Resource.DELETE_ORDER.getAnyParamUrl()).hasAuthority(
                                Role.USER.getAuthority())

                        // worker
                        .requestMatchers(
                                HttpMethod.GET,
                                Resource.DOWNLOAD_ORDER.getAnyParamUrl()).hasAuthority(
                                Role.WORKER.getAuthority())

                        // admin
                        .requestMatchers(
                                HttpMethod.GET,
                                Page.ADMIN.getUrl()).hasAuthority(
                                Role.ADMIN.getAuthority())
                )
                .formLogin(form -> form
                        .loginPage(Page.LOGIN.getUrl()).permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
