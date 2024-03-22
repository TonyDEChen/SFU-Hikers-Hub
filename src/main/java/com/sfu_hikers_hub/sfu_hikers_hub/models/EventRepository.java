package com.sfu_hikers_hub.sfu_hikers_hub.models;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>{
    List<Event> findByEid(int eid);
    
}