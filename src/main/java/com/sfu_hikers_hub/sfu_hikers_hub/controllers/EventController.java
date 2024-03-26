package com.sfu_hikers_hub.sfu_hikers_hub.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sfu_hikers_hub.sfu_hikers_hub.models.Event;
import com.sfu_hikers_hub.sfu_hikers_hub.models.EventRepository;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;


@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepo;
    private UserRepository userRepo;

    @GetMapping("/events/view")
    public String getAllEvents(Model model, HttpSession session){
        System.out.println("Getting all events");
        User user = (User)session.getAttribute("session_user");
        List<Event> events = eventRepo.findAll();

        //sorts view by latest 
        Collections.reverse(events);
        model.addAttribute("events", events);
        model.addAttribute("user", user);
        return "events/viewAllEvents";
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
    public String addEvent(@RequestParam Map<String, String> newevent, HttpServletResponse response, HttpSession session){
        System.out.println("Adding event");
        try{
            User user = (User)session.getAttribute("session_user");
            if(user == null) return "redirect:/login";
            String op = user.getUsername();
            String title = newevent.get("title");
            String location = newevent.get("location");
            String time = newevent.get("time");
            String body = newevent.get("description");
            eventRepo.save(new Event(op, title, location, time, body));
            response.setStatus(201);
        }catch(Exception e){
            System.out.println("Failed to add event");
            return "events/error";
        }
        return "redirect:/events/view";
    }

    @PostMapping("/events/remove")
    public String deleteEvent(@RequestParam("eid") int eid, Model model, HttpSession session){
        System.out.println("removing event: " + eid);
        eventRepo.deleteById(eid);
        return "redirect:/events/view";
    }

    @GetMapping("/events/view/{eid}")
    public String viewEvent(@PathVariable int eid, Model model, HttpSession session){
        try{
            
            User user = (User)session.getAttribute("session_user");
            if(user == null) return "redirect:/login";
            Event event = eventRepo.findByEid(eid);
            if(event == null) return "events/error";

            List<User> attendeeList = new ArrayList<>();
            List<Integer> attendees = event.getAttendees();
            for(int i = 0; i < attendees.size(); i++)
            {
                attendeeList.add(userRepo.findByUid(attendees.get(i)));
            }

            model.addAttribute("event", event);
            model.addAttribute("user", user);
            model.addAttribute("list", attendeeList);
            

            List<Integer> usersInEvent = event.getAttendees();
            for(int i = 0; i < usersInEvent.size(); i++)
            {
                if(user.getUid() == usersInEvent.get(i))
                {
                    //alternate view for users already signed up
                    return "events/viewEventAlt";
                }
            }
            return "events/viewEvent";
        }catch(Exception e){
            System.out.println("Error finding event");
            return "events/error";
        }
    }

    @PostMapping("/events/view/{eid}/signup")
    public String signup4Event(@PathVariable int eid, HttpSession session){
        try {
            User user = (User)session.getAttribute("session_user");
            if(user == null){
                System.out.println("session not found");
                return "events/error";
            }
            System.out.println("current session user "+user.getUsername());
            Event event = eventRepo.findByEid(eid);
            if(event == null){
                System.out.println("event not found");
                return "events/error";
            }
            try {
                event.addAttendee(user.getUid());
                System.out.println("Successfully signed up for event");
                System.out.println("All attendees: "+event.getAttendees());
                eventRepo.save(event);
            } catch (Exception e) {
                System.out.println("error adding attendee to list");
            }

            return "redirect:/events/view/{eid}";
        } catch (Exception e) {
            System.out.println("unknown error");
            return "events/error";
        }
    }
    @PostMapping("/events/view/{eid}/cancel")
    public String cancelSignUp(@PathVariable int eid, HttpSession session)
    {
        User user = (User)session.getAttribute("session_user");
        if(user == null){
            System.out.println("session not found");
            return "redirect:/login";
        }
        Event event = eventRepo.findByEid(eid);
        if(event == null){
            System.out.println("event not found");
            return "events/error";
        }
        event.removeAttendee(user.getUid());
            System.out.println("Successfully cancelled sign up for event");
            System.out.println("All attendees: "+event.getAttendees());
            eventRepo.save(event);

        /* 
        try {
            event.removeAttendee(user.getUid());
            System.out.println("Successfully cancelled sign up for event");
            System.out.println("All attendees: "+event.getAttendees());
            eventRepo.save(event);


        } catch(Exception e) {
            System.out.println("error removing attendee from list");
        }
        */



        return "redirect:/events/view/{eid}";

    }
    
}
