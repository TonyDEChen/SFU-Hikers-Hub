package com.sfu_hikers_hub.sfu_hikers_hub.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "events")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;

    private String op;
    private String title;
    private String location;
    // private String time;
    private LocalDateTime time;
    private long timestamp;
    private String body;
    private List<Integer> attendees = new ArrayList<>();
    private int maxAttendees;
    private double longitude;
    private double latitude;

    public Event()
    {
        // this.attendees = new ArrayList<>();
    }

    public Event(String op, String title, String location, LocalDateTime time, String body, int maxAttendees)
    {
        this.op = op;
        this.title = title;
        this.location = location;
        this.time = time;
        this.body = body;
        this.maxAttendees = maxAttendees;
        // this.attendees = new ArrayList<>();
    }

    public Event(String op, String title, String location, LocalDateTime time, String body, int maxAttendees, double longitude,
            double latitude) {
        this.op = op;
        this.title = title;
        this.location = location;
        this.time = time;
        this.body = body;
        this.maxAttendees = maxAttendees;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Event(String op, String title, String location, LocalDateTime time, long timestamp, String body,
            int maxAttendees, double longitude, double latitude) {
        this.op = op;
        this.title = title;
        this.location = location;
        this.time = time;
        this.timestamp = timestamp;
        this.body = body;
        this.maxAttendees = maxAttendees;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public void removeAttendee(int uid)
    {
        
        System.out.println("removing " + uid);
        for(int i = 0; i < this.attendees.size(); i++)
        {
            System.out.println("test " + i);
            if(Integer.valueOf(uid) == Integer.valueOf(this.attendees.get(i)))
            {
                this.attendees.remove(i);
                break;
            }
        }
    }

    public boolean isFull()
    {
        return attendees.size() >= maxAttendees;
    }

    public int numAttendees()
    {
        return attendees.size();
    }

}
