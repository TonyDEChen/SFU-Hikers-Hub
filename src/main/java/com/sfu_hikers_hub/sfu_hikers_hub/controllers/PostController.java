package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Comparator;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @GetMapping("/posts/view")
    public String getAllPosts(Model model) {
        System.out.println("Getting all posts");
        List<Post> posts = postRepo.findAll(); // findAllByOrderByCreatedAtDesc();
        posts.sort(Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        model.addAttribute("ps", posts);
        return "posts/forumPage";
    }

    @PostMapping("/posts/add")
    public String addPost(@RequestParam Map<String, String> newpost, HttpServletResponse response,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            // This means no session was found
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the response status to 403 Forbidden
            return "redirect:/login"; // Redirect the user to the login page
        }
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            // This means no user is found in session, which implies the user is not logged
            // in
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set the response status to 403 Forbidden
            return "redirect:/login"; // Redirect the user to the login page
        }
        System.out.println("Adding post");
        try {
            String op = user.getUsername(); // "temp";
            String title = newpost.get("title");
            String body = newpost.get("body");

            if (title.length() > 100 || body.length() > 500) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                return "redirect:/posts/add"; // Redirect back to the form with an error message
            }
            postRepo.save(new Post(op, title, body));
            response.setStatus(201);
        } catch (Exception e) {
            System.out.println("FAILED TO ADD POST");
        }
        return "redirect:/posts/view";
    }

    @GetMapping("/posts/test")
    @ResponseBody
    public List<Post> testDatabaseConnection() {
        return postRepo.findAll();
    }

}
