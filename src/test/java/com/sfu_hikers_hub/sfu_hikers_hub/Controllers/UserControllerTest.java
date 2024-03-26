/*package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;

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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserRepository UserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllUsers() throws Exception {

        User u1 = new User();
        u1.setUid(3123);
        u1.setfirstName("Boom");
        u1.setlastName("Bang");
        u1.setEmail("wow@cool.com");
        u1.setUsername("EpicBeast2");
        u1.setPassword("Wowsers1!!!");
        u1.setTotalKm(163);
        u1.setTotalHikes(20);
        u1.setAdmin(false);

        User u2 = new User();
        u2.setUid(3013);
        u2.setfirstName("Frank");
        u2.setlastName("Smith");
        u2.setEmail("fire@emoji.com");
        u2.setUsername("HolySmokes123");
        u2.setPassword("howFire1!!!");
        u2.setTotalKm(107);
        u2.setTotalHikes(15);
        u2.setAdmin(true);


        List<User> users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);

        when(UserRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("users/showAll"))
            .andExpect(MockMvcResultMatchers.model().attribute("users", hasItem(
                allOf(
                    hasProperty("uid",          Matchers.is(3123)),
                    hasProperty("firstName",    Matchers.is("Boom")),
                    hasProperty("lastName",     Matchers.is("Bang")),
                    hasProperty("email",        Matchers.is("wow@cool.com")),
                    hasProperty("username",     Matchers.is("EpicBeast2")),
                    hasProperty("password",     Matchers.is("Wowsers1!!!")),
                    hasProperty("totalKm",      Matchers.is(163)),
                    hasProperty("totalHikes",   Matchers.is(20)),
                    hasProperty("isAdmin",      Matchers.is(false)))
                )))
            .andExpect(MockMvcResultMatchers.model().attribute("users", hasItem(
                        allOf(
                            hasProperty("uid",          Matchers.is(3013)),
                            hasProperty("firstName",    Matchers.is("Frank")),
                            hasProperty("lastName",     Matchers.is("Smith")),
                            hasProperty("email",        Matchers.is("fire@emoji.com")),
                            hasProperty("username",     Matchers.is("HolySmokes123")),
                            hasProperty("password",     Matchers.is("howFire1!!!")),
                            hasProperty("totalKm",      Matchers.is(107)),
                            hasProperty("totalHikes",   Matchers.is(15)),
                            hasProperty("isAdmin",      Matchers.is(true)))
                        )
                ));
    }
}

*/