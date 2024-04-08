package com.sfu_hikers_hub.sfu_hikers_hub.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
public class Photo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = true, length = 255)
  private String title; // For titles for images.

  @Column(name = "filename")
  private String filename; // The name of the file stored on the server.

  @Column(name = "upload_time")
  private LocalDateTime uploadTime;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "uid")
  // @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  // Constructors
  public Photo() {
    // No-argument constructor
  }

  public Photo(String title, String filename, User user) {
    this.title = title;
    this.filename = filename;
    this.uploadTime = LocalDateTime.now();
    this.user = user;
  }

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public LocalDateTime getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(LocalDateTime uploadTime) {
    this.uploadTime = uploadTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
