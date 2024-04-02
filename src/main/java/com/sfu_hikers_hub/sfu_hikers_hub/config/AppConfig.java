package com.sfu_hikers_hub.sfu_hikers_hub.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
