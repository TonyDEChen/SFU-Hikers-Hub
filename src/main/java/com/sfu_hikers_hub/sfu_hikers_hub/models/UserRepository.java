package com.sfu_hikers_hub.sfu_hikers_hub.models;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    //List<User> findByAdmin(boolean isAdmin);
    User findByUid(int uid);
    //can add more as we go  
}
