package com.example.beerservice.service.inventory;

import com.example.beerservice.service.inventory.config.BeerInventoryServiceConfiguration;
import com.example.beerservice.service.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Service
public class RestTemplateBeerInventoryService implements BeerInventoryService {

    static private final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    private final RestTemplate restTemplate;
    private final BeerInventoryServiceConfiguration beerInventoryServiceConfiguration;

    public RestTemplateBeerInventoryService(final RestTemplateBuilder restTemplateBuilder,
                                            final BeerInventoryServiceConfiguration beerInventoryServiceConfiguration) {
        this.restTemplate = restTemplateBuilder.build();
        this.beerInventoryServiceConfiguration = beerInventoryServiceConfiguration;
    }

    @Override
    public Integer getOnHandInventory(final UUID beerId) {
        log.debug("Calling Inventory Service");

        final ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(
                beerInventoryServiceConfiguration.host() + INVENTORY_PATH,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, beerId);

        return Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::quantityOnHand)
                .sum();
    }
}
