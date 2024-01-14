package com.example.beerservice.service.inventory;

import com.example.beerservice.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Profile("local-discovery")
@Service
@RequiredArgsConstructor
public class FeignBeerInventoryService implements BeerInventoryService {

    private final BeerInventoryServiceFeignClient beerInventoryService;

    @Override
    public Integer getOnHandInventory(final UUID beerId) {
        log.debug("Calling Inventory Service");

        final ResponseEntity<List<BeerInventoryDto>> responseEntity = beerInventoryService.getOnHandInventory(beerId);

        int onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::quantityOnHand)
                .sum();
        log.debug("beer id: {}, on hand: {}", beerId, onHand);

        return onHand;
    }
}
