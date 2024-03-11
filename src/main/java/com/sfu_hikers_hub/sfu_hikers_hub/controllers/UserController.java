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
import org.springframework.ui.*;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/adminDashboard")
    public String getAllUsers(Model model)
    {
        System.out.println("getting all users");

        List<User> users = userRepo.findAll();

        model.addAttribute("us", users);
        
        return "users/adminDashboard";
    }

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
}
