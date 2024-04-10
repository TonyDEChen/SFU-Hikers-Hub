package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.sfu_hikers_hub.sfu_hikers_hub.controllers.EventController;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Event;
import com.sfu_hikers_hub.sfu_hikers_hub.models.EventRepository;
import com.sfu_hikers_hub.sfu_hikers_hub.models.UserRepository;

import io.github.cdimascio.dotenv.Dotenv;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @MockBean
    private EventRepository EventRepository;

    @MockBean
    private UserRepository UserRepository;

    @Autowired
    private MockMvc mockMvc;

    Dotenv dotenv;
    private String apiKey;
    private String apiKeyWeather;

    public EventControllerTest(){
        try {
            dotenv = Dotenv.configure().directory("/etc/secrets/").load();
        } catch (Exception e) {
            dotenv = Dotenv.load();
        }
        this.apiKey = dotenv.get("MAPS_KEY");
        this.apiKeyWeather = dotenv.get("WEATHER_KEY");
    }

    @Test
    void testGetAllEvents() throws Exception {

        Event e1 = new Event();
        LocalDateTime time = LocalDateTime.now();
        e1.setOp("wow");
        e1.setTitle("Big Event");
        e1.setLocation("Big Place");
        e1.setTime(time);
        e1.setBody("Wowsers Event!");

        Event e2 = new Event();
        LocalDateTime time2 = LocalDateTime.of(2024, 5, 2, 14, 29);
        e2.setOp("hehy!");
        e2.setTitle("smaller event");
        e2.setLocation("Small Place");
        e2.setTime(time2);
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
                    hasProperty("time",     Matchers.is(time)),
                    hasProperty("body",     Matchers.is("Wowsers Event!"))
                )
            )))
            .andExpect(MockMvcResultMatchers.model().attribute("events", hasItem(
                        allOf(
                                hasProperty("op",       Matchers.is("hehy!")),
                                hasProperty("title",    Matchers.is("smaller event")),
                                hasProperty("location", Matchers.is("Small Place")),
                                hasProperty("time",     Matchers.is(time2)),
                                hasProperty("body",     Matchers.is("Smaller wow event"))
                        )
                )));
    }

    @Test
    public void testGoogleApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=" + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testOpenWeatherApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + apiKeyWeather;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}
