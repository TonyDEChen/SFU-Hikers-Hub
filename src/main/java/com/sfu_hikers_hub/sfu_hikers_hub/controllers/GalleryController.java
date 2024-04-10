package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.sfu_hikers_hub.sfu_hikers_hub.models.User;

@Controller
public class GalleryController {

  @GetMapping("/gallery/view")
  public String gallery(Model model, HttpSession session) {
      User user = (User) session.getAttribute("session_user");
      model.addAttribute("user", user);
      return "gallery/gallery";
  }
}
