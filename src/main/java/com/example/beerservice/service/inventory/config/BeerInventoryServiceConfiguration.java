package com.example.beerservice.service.inventory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "beer-inventory-service", ignoreUnknownFields = false)
public record BeerInventoryServiceConfiguration(String host) {
}
