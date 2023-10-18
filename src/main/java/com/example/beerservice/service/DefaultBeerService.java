package com.example.beerservice.service;

import com.example.beerservice.repository.BeerRepository;
import com.example.beerservice.web.model.BeerDto;
import com.example.beerservice.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBeerService implements BeerService {

    private final BeerRepository beerRepository;
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.ALE)
                .build();
    }

    @Override
    public BeerDto create(final BeerDto beerDto) {
        return BeerDto.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void update(final UUID id, final BeerDto beerDto) {

    }
}
