package com.sfu_hikers_hub.sfu_hikers_hub.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String name;
    private String password;
    private int totalKm;
    private int totalHikes;
    //probably add more fields later for the profile stats feature
    /* 
    POSTGRESSQL TABLE FORMAT:
    uid SERIAL, name VARCHAR, numkm INTEGER, numhikes INTEGER, password VARCHAR
    */

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {}

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    public void setTotalHikes(int totalHikes) {
        this.totalHikes = totalHikes;
    }

    public int getTotalHikes() {
        return totalHikes;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}