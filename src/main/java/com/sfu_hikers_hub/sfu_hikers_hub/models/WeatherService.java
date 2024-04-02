package com.sfu_hikers_hub.sfu_hikers_hub.models;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    //load this via the local file
    //@Value("${WEATHER_KEY}")
    private String apiKey = System.getenv("WEATHER_KEY");
    
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
