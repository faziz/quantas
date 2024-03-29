package com.faziz.quantas.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AirportLocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportLocationApplication.class, args);
    }
}
