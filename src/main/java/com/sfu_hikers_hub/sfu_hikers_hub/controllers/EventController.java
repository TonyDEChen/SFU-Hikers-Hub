package com.sfu_hikers_hub.sfu_hikers_hub.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfu_hikers_hub.sfu_hikers_hub.models.Event;
import com.sfu_hikers_hub.sfu_hikers_hub.models.EventRepository;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @GetMapping("/events/view")
    public String getAllEvents(Model model){
        System.out.println("Getting all events");
        List<Event> events = eventRepo.findAll();
        model.addAttribute("events", events);
        return "events/viewEvents";
    }

    @GetMapping("/events/add")
    public String showAdd(HttpSession session){
        User user = (User)session.getAttribute("session_user");
        if(user==null){
            return "redirect:/login";
        }else if(user.isAdmin()){
            return "events/addEvent";
        }
        return "events/error";
    }

    @PostMapping("/events/add")
    public String addEvent(@RequestParam Map<String, String> newevent, HttpServletResponse response){
        //WIP
        return "";
    }
    
}
