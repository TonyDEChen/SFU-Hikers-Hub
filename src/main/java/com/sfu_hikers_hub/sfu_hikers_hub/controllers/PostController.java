package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepo;
    
    @GetMapping("/posts/view")
    public String getAllPosts(Model model){
        System.out.println("Getting all posts");
        List<Post> posts = postRepo.findAll();
        model.addAttribute("ps", posts);
        return "posts/forumPage";
    }
}
