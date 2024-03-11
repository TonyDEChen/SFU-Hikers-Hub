package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public String addStudent(@RequestParam Map<String, String> newUser, HttpServletResponse response) {
        String firstName = newUser.get("firstName");
        String lastName = newUser.get("lastName");
        String username = newUser.get("username");
        String email = newUser.get("email");
        String password = newUser.get("password");

        if (userRepo.findByUsername(username) != null) {
            response.setStatus(409);
            return "failure"; // <--- doesn't exist yet
        }

        if (userRepo.findByEmail(email) != null) {
            response.setStatus(409);
            return "failure"; // <--- doesn't exist yet
        }

        userRepo.save(new User(firstName, lastName, email, username, password));

        response.setStatus(201);
        return "success"; // <--- doesn't exist yet
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            Model model) {
        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Login successful, proceed to the main page
            return "welcome";
        } else {
            // Login failed, return to the login page with an error message
            model.addAttribute("errorMessage", "Invalid username or password");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "login";
        }
    }

}
