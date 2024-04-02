package com.sfu_hikers_hub.sfu_hikers_hub.models;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class WeatherService {

    //load this via the local file
    //@Value("${WEATHER_KEY}")
    Dotenv dotenv = Dotenv.load();
    private String apiKey = dotenv.get("WEATHER_KEY");
    
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
