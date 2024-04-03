package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/weather")
public class OpenWeatherController {

    Dotenv dotenv = Dotenv.load();
    private String apiKey = dotenv.get("WEATHER_KEY");
    
    private final RestTemplate restTemplate;

    public OpenWeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/getWeatherKey")
    public String getOpenWeatherKey()
    {
        System.out.println(apiKey);
        return apiKey;
    }

    /* 
    @GetMapping("/callWeather")
    public String getWeatherData(@RequestParam double latitude, @RequestParam double longitude) {
        https://history.openweathermap.org/data/2.5/aggregated/year?lat=35&lon=139&appid={API key}
    }
    */
    




    
}
