package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sfu_hikers_hub.sfu_hikers_hub.controllers.UserController;
import com.sfu_hikers_hub.sfu_hikers_hub.models.User;
import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserRepository UserRepository;

    @MockBean
    private PostRepository PostRepository;

    @Autowired
    private MockMvc mockMvc;


    @BeforeAll
    static void setup() {
        System.out.println("Setting up");
    }

    @Test
    public void testRedirect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));
    }

    @Test
    public void testGetLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/login"));
    }

    @Test
    public void testGetRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/register"));
    }

    @Test
    public void testChangePassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/changePassword"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/changePassword"));
    }

    @Test
    public void testProfileRedirect() throws Exception {
        User u1 = new User();
        u1.setFirstName("Wow");
        u1.setLastName("Crazy");
        u1.setPassword("Woah!");
        u1.setEmail("cool.gmail.com");
        u1.setTotalHikes(4);
        u1.setTotalKm(300);
        u1.setUid(12712);
        u1.setUsername("epic");
        u1.setAdmin(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/userProfile/epic"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

}

