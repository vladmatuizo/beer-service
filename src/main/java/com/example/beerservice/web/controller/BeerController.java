package com.example.beerservice.web.controller;

import com.example.beerservice.service.BeerService;
import com.example.common.model.BeerDto;
import com.example.common.model.BeerPagedList;
import com.example.common.model.BeerStyle;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class
BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping("/beer")
    public BeerPagedList listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "beerName", required = false) String beerName,
                                   @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                   @RequestParam(value = "showInventoryOnHand", required = false, defaultValue = "false") boolean showInventoryOnHand) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
    }

    @GetMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
                                           @RequestParam(value = "showInventoryOnHand", required = false, defaultValue = "false") boolean showInventoryOnHand) {
        return ResponseEntity.ok(beerService.getBeerById(beerId, showInventoryOnHand));
    }
    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable String upc) {
        return ResponseEntity.ok(beerService.getBeerByUpc(upc));
    }

    @PostMapping("/beer")
    public ResponseEntity<?> createBeer(@Valid @RequestBody BeerDto beerDto) {
        final BeerDto savedBeer = beerService.create(beerDto);
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/beer/%s", savedBeer.getId().toString())))
                .build();
    }

    @PutMapping("/beer/{id}")
    public ResponseEntity<?> updateBeer(@PathVariable UUID id, @Valid @RequestBody BeerDto beerDto) {
        beerService.update(id, beerDto);
        return ResponseEntity.noContent().build();
    }

}

