package com.sfu_hikers_hub.sfu_hikers_hub.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByPid(int pid);

    List<Post> findAllByOrderByCreatedAtDesc(); // Sort by most recent first
}
