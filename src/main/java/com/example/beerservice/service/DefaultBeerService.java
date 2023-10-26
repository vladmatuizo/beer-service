package com.example.beerservice.service;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.repository.BeerRepository;
import com.example.beerservice.web.exception.NotFoundException;
import com.example.beerservice.web.mapper.BeerMapper;
import com.example.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBeerService implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID id) {
        return beerMapper.mapToBeerDto(beerRepository.findById(id)
                .orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto create(final BeerDto beerDto) {
        final Beer beer = beerMapper.mapToBeer(beerDto);
        return beerMapper.mapToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void update(final UUID id, final BeerDto beerDto) {
        final Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        beerRepository.save(beer);
    }
}
