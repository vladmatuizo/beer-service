package com.example.beerservice.web.controller;

import com.example.beerservice.service.BeerService;
import com.example.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable UUID beerId){
        return ResponseEntity.ok(beerService.getBeerById(beerId));
    }

    @PostMapping("/")
    public ResponseEntity<?> createBeer(@RequestBody BeerDto beerDto) {
        final BeerDto savedBeer = beerService.create(beerDto);
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/beer/%s", savedBeer.getId().toString())))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeer(@PathVariable UUID id, @RequestBody BeerDto beerDto) {
        beerService.update(id, beerDto);
        return ResponseEntity.noContent().build();
    }

}

