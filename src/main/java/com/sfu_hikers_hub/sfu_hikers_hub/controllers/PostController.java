package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepo;
    
}
