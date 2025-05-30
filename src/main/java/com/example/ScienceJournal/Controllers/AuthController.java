package com.example.ScienceJournal.Controllers;

import com.example.ScienceJournal.Configs.WebSecurityConfig;
import com.example.ScienceJournal.Entities.User;
import com.example.ScienceJournal.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    UserService uService;
    @Autowired
    WebSecurityConfig securityConfig;

    @GetMapping("/authentication")
    public String authenticate() {
        return "authenticationPage";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registrationPage";
    }
    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password) {
        uService.registration(new User(username, securityConfig.encoder().encode(password)));
        return "redirect:/authentication";
    }
}
