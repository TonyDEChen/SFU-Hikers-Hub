package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Photo;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PhotoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class GalleryController {

  private final PhotoRepository photoRepository;

  @Autowired
  public GalleryController(PhotoRepository photoRepository) {
    this.photoRepository = photoRepository;
  }

  @GetMapping("/gallery/view")
  public String viewGallery(Model model) {
    model.addAttribute("photos", photoRepository.findAll());
    return "gallery/photoGallery";
  }

  @GetMapping("/gallery/add")
  public String showAddPhotoForm() {
    return "gallery/addPhoto"; // Path to your add photo form HTML
  }

  @PostMapping("/gallery/add")
  public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image,
      HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) {

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
    System.out.println("Adding photo");
    try {

      String filename = handleImageUpload(image);

      photoRepository.save(new Photo(title, filename, user));
      response.setStatus(HttpServletResponse.SC_CREATED); // Set the response status to 201 Created
    } catch (Exception e) {
      System.out.println("FAILED TO ADD PHOTO");
    }
    return "redirect:/gallery/view";
  }

  private static final String UPLOAD_DIR = "uploads/";

  private String handleImageUpload(MultipartFile image) throws IOException {
    if (image.isEmpty()) {
      throw new IOException("Failed to upload empty file");
    }

    // Generate a unique filename using UUID
    String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

    // Resolve the path to the upload directory
    Path uploadPath = Paths.get(UPLOAD_DIR);

    // If the upload directory doesn't exist, create it
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    // Copy the file to the upload directory
    Path filePath = uploadPath.resolve(filename);
    Files.copy(image.getInputStream(), filePath);

    return filename;
  }

}
