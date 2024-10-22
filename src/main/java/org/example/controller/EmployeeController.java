package org.example.controller;

import org.example.model.constant.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link Page#EMPLOYEE}
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping
    public String getEmployeePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return Page.EMPLOYEE.getTemplate();
    }
}
