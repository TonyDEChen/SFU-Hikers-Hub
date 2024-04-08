package com.sfu_hikers_hub.sfu_hikers_hub.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
  Photo findById(long id); // Method to find a photo by its ID
}
