package com.sfu_hikers_hub.sfu_hikers_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfuHikersHubApplication {

	public static void main(String[] args) {

		System.getenv().forEach((k, v) -> {
			System.out.println(k + ":" + v);
		});
		SpringApplication.run(SfuHikersHubApplication.class, args);
	}

}
