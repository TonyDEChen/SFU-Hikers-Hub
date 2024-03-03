package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;
    
}
