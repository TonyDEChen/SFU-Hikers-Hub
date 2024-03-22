package com.sfu_hikers_hub.sfu_hikers_hub.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.sfu_hikers_hub.sfu_hikers_hub.models.User;

@Entity
@Table(name = "events")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;

    private String op;
    private String title;
    private String location;
    private String time;
    private String body;
    private List<Integer> attendees = new ArrayList<>();

    public Event()
    {
        // this.attendees = new ArrayList<>();
    }

    public Event(String op, String title, String location, String time, String body)
    {
        this.op = op;
        this.title = title;
        this.location = location;
        this.time = time;
        this.body = body;
        // this.attendees = new ArrayList<>();
    }

    public int getEid() {
        return eid;
    }
    
    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Integer> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Integer> attendees) {
        this.attendees = attendees;
    }

    public void addAttendee(int uid){
        System.out.println("adding "+uid);
        Integer id = Integer.valueOf(uid);
        this.attendees.add(id);
        // for(int i = 0; i < this.attendees.size(); i++){
            // System.out.println(this.attendees);
        // }
    }

    
}
