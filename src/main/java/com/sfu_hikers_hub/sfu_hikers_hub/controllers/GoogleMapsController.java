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


@RestController
@RequestMapping("/api")
public class GoogleMapsController {

    //@Value("${gApiKey}")
    //private String apiKey;
    //private String apiKey = System.getenv("WEATHER_KEY");
    
    // Dotenv dotenv = Dotenv.load();
    // Dotenv dotenv = Dotenv.configure().filename("keys.env").load();
    Dotenv dotenv = Dotenv.configure().directory("/etc/secrets/").load();
    private String apiKey = dotenv.get("MAPS_KEY");
    


    private final RestTemplate restTemplate;

    public GoogleMapsController(RestTemplate restTemplate) 
    {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/google-maps-api")
    public String getGoogleMapsApiKey()
    {
        /* 
        String mapsApiKey = apiKey;
        String url = "https://maps.googleapis.com/maps/api/js?key=" + mapsApiKey;

        System.out.println("pluh");

        return url;
        */
        System.out.println(apiKey);
        return apiKey;
    }

    @GetMapping("/google-maps-data")
    public ResponseEntity<String> getGoogleMapsData() {
        String url = "https://maps.googleapis.com/maps/api/js?key=" + apiKey;
        String responseData = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/google-maps-url")
    public String getGoogleMapsURL() {

        return "https://maps.googleapis.com/maps/api/js?key=" + apiKey;
    }
    
}
