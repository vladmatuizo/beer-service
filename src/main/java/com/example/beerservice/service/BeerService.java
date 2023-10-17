package com.example.beerservice.service;

import com.example.beerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID id);

    BeerDto create(BeerDto beerDto);

    void update(UUID id, BeerDto beerDto);
}
