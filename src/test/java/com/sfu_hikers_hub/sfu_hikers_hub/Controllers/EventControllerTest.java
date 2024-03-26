package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

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

import com.sfu_hikers_hub.sfu_hikers_hub.controllers.EventController;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Event;
import com.sfu_hikers_hub.sfu_hikers_hub.models.EventRepository;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @MockBean
    private EventRepository EventRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllEvents() throws Exception {

        Event e1 = new Event();
        e1.setOp("wow");
        e1.setTitle("Big Event");
        e1.setLocation("Big Place");
        e1.setTime("10:30");
        e1.setBody("Wowsers Event!");

        Event e2 = new Event();
        e2.setOp("hehy!");
        e2.setTitle("smaller event");
        e2.setLocation("Small Place");
        e2.setTime("7:30");
        e2.setBody("Smaller wow event");

        List<Event> events = new ArrayList<Event>();
        events.add(e1);
        events.add(e2);

        when(EventRepository.findAll()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/view"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attribute("events", hasItem(
                allOf(
                    hasProperty("op",       Matchers.is("wow")),
                    hasProperty("title",    Matchers.is("Big Event")),
                    hasProperty("location", Matchers.is("Big Place")),
                    hasProperty("time",     Matchers.is("10:30")),
                    hasProperty("body",     Matchers.is("Wowsers Event!"))
                )
            )))
            .andExpect(MockMvcResultMatchers.model().attribute("events", hasItem(
                        allOf(
                                hasProperty("op",       Matchers.is("hehy!")),
                                hasProperty("title",    Matchers.is("smaller event")),
                                hasProperty("location", Matchers.is("Small Place")),
                                hasProperty("time",     Matchers.is("7:30")),
                                hasProperty("body",     Matchers.is("Smaller wow event"))
                        )
                )));
    }
}