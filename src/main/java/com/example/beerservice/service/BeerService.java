package com.example.beerservice.service;

import com.example.common.model.BeerDto;
import com.example.common.model.BeerPagedList;
import com.example.common.model.BeerStyle;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest, boolean showInventoryOnHand);
    BeerDto getBeerById(UUID id, boolean showInventoryOnHand);
    BeerDto getBeerByUpc(String upc);

    BeerDto create(BeerDto beerDto);

    void update(UUID id, BeerDto beerDto);
}
