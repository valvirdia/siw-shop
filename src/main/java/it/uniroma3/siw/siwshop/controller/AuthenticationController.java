package it.uniroma3.siw.siwshop.controller;

import it.uniroma3.siw.siwshop.model.Credentials;
import it.uniroma3.siw.siwshop.model.User;
import it.uniroma3.siw.siwshop.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/success";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @ModelAttribute Credentials credentials) {
        credentials.setUser(user);
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        credentialsService.save(credentials);
        return "redirect:/login";
    }

    @GetMapping("/success")
    public String successRedirect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/home";
    }
}
