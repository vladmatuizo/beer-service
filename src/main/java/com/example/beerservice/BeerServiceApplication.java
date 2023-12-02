package com.example.beerservice;

import com.example.beerservice.service.inventory.config.BeerInventoryServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({BeerInventoryServiceConfiguration.class})
@SpringBootApplication
public class BeerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerServiceApplication.class, args);
    }

}
