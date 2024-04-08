package com.sfu_hikers_hub.sfu_hikers_hub.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPid(int pid);
    List<Post> findAllPostsByOp(String op);
    List<Post> findAllByOrderByCreatedAtDesc(); // Sort by most recent first
}
