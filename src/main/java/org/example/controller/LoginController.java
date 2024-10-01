package org.example.controller;

import org.example.model.constant.Page;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * {@link Page#REGISTRATION}
     */
    @GetMapping("/registration")
    public String registration() {
        return Page.REGISTRATION.getTemplate();
    }

    /**
     * {@link Page#REGISTRATION}
     */
    @PostMapping("/registration")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        loginService.register(username, password);
        return Page.LOGIN.getTemplate();
    }
}
