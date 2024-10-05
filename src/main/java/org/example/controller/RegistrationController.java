package org.example.controller;

import org.example.model.constant.Page;
import org.example.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * {@link Page#REGISTRATION}
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registration() {
        return Page.REGISTRATION.getTemplate();
    }

    @PostMapping
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        registrationService.registerUser(username, password);
        return Page.LOGIN.getTemplate();
    }
}
