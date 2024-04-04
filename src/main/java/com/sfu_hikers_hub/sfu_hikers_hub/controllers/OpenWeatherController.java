package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.sfu_hikers_hub.sfu_hikers_hub.config.AppConfig;

import io.micrometer.core.ipc.http.HttpSender.Response;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.cdimascio.dotenv.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/weather")
public class OpenWeatherController {

    private String apiKey = System.getenv("WEATHER_KEY");
    /* 
    Dotenv dotenv = Dotenv.load();
    private String apiKey = dotenv.get("WEATHER_KEY");
    */
    
    private final RestTemplate restTemplate;

    public OpenWeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/getWeatherKey")
    public ResponseEntity<String> getOpenWeatherKey()
    {
        System.out.println("gruh");
        System.out.println(apiKey);
        return ResponseEntity.ok(apiKey);
    }

    /* 
    @GetMapping("/callWeather")
    public String getWeatherData(@RequestParam double latitude, @RequestParam double longitude) {
        https://history.openweathermap.org/data/2.5/aggregated/year?lat=35&lon=139&appid={API key}
    }
    */
    




    
}
