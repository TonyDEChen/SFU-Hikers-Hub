package com.sfu_hikers_hub.sfu_hikers_hub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/weather")
public class OpenWeatherController {

    //@Value("${wApiKey}")
    //private String apiKey;
    //private String apiKey = System.getenv("WEATHER_KEY");
     
    // Dotenv dotenv = Dotenv.load();
    // Dotenv dotenv = Dotenv.configure().filename("keys.env").load();

    // Dotenv dotenv = Dotenv.configure().directory("/etc/secrets/").load();
    // private String apiKey = dotenv.get("WEATHER_KEY");
    
    Dotenv dotenv;
    private String apiKey;

    public OpenWeatherController(RestTemplate restTemplate) {
        try {
            dotenv = Dotenv.configure().directory("/etc/secrets/").load();
        } catch (Exception e) {
            dotenv = Dotenv.load();
        }
        this.apiKey = dotenv.get("WEATHER_KEY");
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
