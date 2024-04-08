package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostRepository postRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        } else if (user.isAdmin()) {
            List<User> users = userRepo.findAll();

            model.addAttribute("us", users);
            for(int i = 0; i < users.size(); i++) {
                 System.out.println(users.get(i).getUsername() + " " + users.get(i).isAdmin());
            }
            
            return "users/adminDashboard";
        } else {
            model.addAttribute("user", user);
            return "users/dashboard";
        }
    }

    @PostMapping("/register")
    public String addStudent(@RequestParam Map<String, String> newUser, HttpServletResponse response, Model model) {
        String firstName = newUser.get("firstName");
        String lastName = newUser.get("lastName");
        String username = newUser.get("username");
        String email = newUser.get("email");
        String password = newUser.get("password");

        if (userRepo.findByUsername(username) != null) {
            response.setStatus(409);
            model.addAttribute("errorMessage", "Username already exists");
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            return "users/register";
        }

        if (username.length() > 24) {
            model.addAttribute("errorMessage", "Username must be 24 characters or less.");
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            return "users/register";
        }
        
        if (userRepo.findByEmail(email) != null) {
            response.setStatus(409);
            model.addAttribute("errorMessage", "Email already exists");
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("username", username);
            return "users/register";
        }

        userRepo.save(new User(firstName, lastName, email, username, password));

        response.setStatus(201);
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Login successful, proceed to the main page
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            // Login failed, return to the login page with an error message
            model.addAttribute("errorMessage", "Invalid username or password");
            model.addAttribute("username", username);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "users/login";
        }
    }

    @PostMapping("/promote")
    public String promoteUser(@RequestParam("uid") int uid, Model model) {
        User promotingUser = userRepo.findByUid(uid);

        System.out.println("promoting: " + promotingUser.getUsername());
        promotingUser.setAdmin(true);

        userRepo.save(promotingUser);

        //User promotedUser = new User(promotingUser.getFirstName(), promotingUser.getLastName(), promotingUser.getEmail(), promotingUser.getUsername(), promotingUser.getPassword());
        //promotedUser.setTotalHikes(promotedUser.getTotalHikes());
        //promotedUser.setTotalKm(promotedUser.getTotalKm());
        //promotedUser.setAdmin(true);

        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        
        return "users/adminDashboard";
    }

    @PostMapping("/updateFirstName")
    public String updateFirstName(@RequestParam("firstName") String firstName, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        user.setFirstName(firstName);
        userRepo.save(user);
        return "redirect:/account";
    }

    @PostMapping("/updateLastName")
    public String updateLastName(@RequestParam("lastName") String lastName, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        user.setLastName(lastName);
        userRepo.save(user);
        return "redirect:/account";
    }

    @PostMapping("/updateUsername")
    public String updateUsername(@RequestParam("username") String username, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }

        if (userRepo.findByUsername(username) != null) {
            if (userRepo.findByUsername(username).getUid() != user.getUid()) {
                model.addAttribute("user", user);
                model.addAttribute("errorMessage", "Username already exists");
                return "users/account";
            } else {
                return "redirect:/account";
            }
        }

        user.setUsername(username);
        userRepo.save(user);
        return "redirect:/account";
    }

    @PostMapping("/updateEmail")
    public String updateEmail(@RequestParam("email") String email, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }

        if (userRepo.findByEmail(email) != null) {
            if (userRepo.findByEmail(email).getUid() != user.getUid()) {
                model.addAttribute("user", user);
                model.addAttribute("errorMessage", "Email already exists");
                return "users/account";
            } else {
                return "redirect:/account";
            }
        }

        user.setEmail(email);
        userRepo.save(user);
        return "redirect:/account";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("old-password") String oldPassword,
                                 @RequestParam("password") String Password,
                                 @RequestParam("confirm-password") String confirmPassword,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        if (!user.getPassword().equals(oldPassword)) {  
            model.addAttribute("errorMessage", "Incorrect old password.");
            return "users/changePassword";
        }
        if (!(Password.equals(confirmPassword))) {
            model.addAttribute("errorMessage", "New password and confirm password are not the same.");
            System.out.println("New Password: " + Password + "\nConfirm Password: " + confirmPassword);
            return "users/changePassword";
        }
        user.setPassword(Password);
        userRepo.save(user);
        return "redirect:/dashboard";
    }
    
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("session_user");
        } else {
            user = null;
        }
        model.addAttribute("us", user);
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "users/register";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "users/login";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/changePassword")
    public String changePassword() {
        return "users/changePassword";
    }

    @GetMapping("/account")
    public String account(Model model, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("user", user);
            return "users/account";
        }
    }

    @GetMapping("/userProfile/{username}")
    public String showUserProfile(@PathVariable String username, Model model, HttpSession session) {
    User user = userRepo.findByUsername(username);
    User loggedInUser = (User) session.getAttribute("session_user");

    if (user == null) {
        return "redirect:/login";
    } else {
        model.addAttribute("profileUser", user);
        model.addAttribute("user", loggedInUser);

        List<Post> userPosts = postRepo.findAllPostsByOp(username);
        model.addAttribute("userPosts", userPosts);
    }
    return "users/userProfile";
}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }
}
