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

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;


@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserRepository userRepo;

    Dotenv dotenv = Dotenv.load();
    private String apiKey = dotenv.get("MAPS_KEY");
    private String apiKeyWeather = dotenv.get("WEATHER_KEY");

    @GetMapping("/events/view")
    public String getAllEvents(Model model, HttpSession session){
        System.out.println("Getting all events");
        User user = (User)session.getAttribute("session_user");
        List<Event> events = eventRepo.findAll();

        //sorts view by latest 
        Collections.reverse(events);
        model.addAttribute("events", events);
        model.addAttribute("user", user);
        System.out.println("test1");
        return "events/viewAllEvents";
    }

    @GetMapping("/events/add")
    public String showAdd(HttpSession session, Model model){
        User user = (User)session.getAttribute("session_user");
        if(user==null){
            return "redirect:/login";
        }else if(user.isAdmin()){
            model.addAttribute("apiKey", apiKey);
            return "events/addEvent";
        }
        return "events/error";
    }

    @PostMapping("/events/add")
    public String addEvent(@RequestParam Map<String, String> newevent, @RequestParam(name = "time") LocalDateTime time, HttpServletResponse response, HttpSession session){
        System.out.println("Adding event");
        try{
            User user = (User)session.getAttribute("session_user");
            if(user == null) return "redirect:/login";
            String op = user.getUsername();
            String title = newevent.get("title");
            String location = newevent.get("location");
            // String time = newevent.get("time");
            String body = newevent.get("description");
            int maxAttendees = Integer.parseInt(newevent.get("maxnum"));
            double longitude = Double.parseDouble(newevent.get("longitude"));
            double latitude = Double.parseDouble(newevent.get("latitude"));

            ZoneId pacificZoneId = ZoneId.of("America/Vancouver");
            ZonedDateTime pacific = ZonedDateTime.of(time, pacificZoneId);
            ZonedDateTime utc = pacific.withZoneSameInstant(pacificZoneId);

            long timestamp = utc.toInstant().getEpochSecond();

            // eventRepo.save(new Event(op, title, location, time, body, maxAttendees));
            // eventRepo.save(new Event(op, title, location, time, body, maxAttendees, longitude, latitude));
            eventRepo.save(new Event(op, title, location, time, timestamp, body, maxAttendees, longitude, latitude));

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
                System.out.println(userRepo.findByUid(attendees.get(i)).getFirstName());
                attendeeList.add(userRepo.findByUid(attendees.get(i)));
            }

            model.addAttribute("event", event);
            model.addAttribute("user", user);
            model.addAttribute("list", attendeeList);
            model.addAttribute("apiKey", apiKey);
            model.addAttribute("weatherkey", apiKeyWeather);

            model.addAttribute("lng", event.getLongitude());
            model.addAttribute("lat", event.getLatitude());

            model.addAttribute("timestamp", event.getTimestamp());


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

    @GetMapping("/events/map-test")
    public String mapTest(Model model){
        model.addAttribute("apiKey", apiKey);
        System.out.println(apiKey);
        return "events/mapTest";
    }
}
