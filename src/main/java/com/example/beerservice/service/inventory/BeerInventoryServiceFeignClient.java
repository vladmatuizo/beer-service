package com.example.beerservice.service.inventory;

import com.example.beerservice.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

import static com.example.beerservice.service.inventory.RestTemplateBeerInventoryService.INVENTORY_PATH;


@FeignClient(name = "inventory-service")
public interface BeerInventoryServiceFeignClient {
    @GetMapping(INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable final UUID beerId);
}
