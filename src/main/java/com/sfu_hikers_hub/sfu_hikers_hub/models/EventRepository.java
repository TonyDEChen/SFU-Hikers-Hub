package com.sfu_hikers_hub.sfu_hikers_hub.models;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer>{
    Event findByEid(int eid);
    void deleteById(int eid);
}
