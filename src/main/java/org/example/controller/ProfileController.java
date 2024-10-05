package org.example.controller;

import org.example.model.constant.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link Page#PROFILE}
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String getProfile() {
        return Page.PROFILE.getTemplate();
    }
}
