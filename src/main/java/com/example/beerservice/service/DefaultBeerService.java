package com.example.beerservice.service;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.repository.BeerRepository;
import com.example.beerservice.web.exception.NotFoundException;
import com.example.beerservice.web.mapper.BeerMapper;
import com.example.common.model.BeerDto;
import com.example.common.model.BeerPagedList;
import com.example.common.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultBeerService implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(final String beerName, final BeerStyle beerStyle,
                                   final PageRequest pageRequest, final boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(showInventoryOnHand
                        ? beerMapper::mapToBeerDtoWithInventory
                        : beerMapper::mapToBeerDto)
                .collect(Collectors.toList()),
                beerPage.getPageable(),
                beerPage.getTotalElements());

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#id", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getBeerById(final UUID id, final boolean showInventoryOnHand) {
        final Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);

        return showInventoryOnHand ? beerMapper.mapToBeerDtoWithInventory(beer) : beerMapper.mapToBeerDto(beer);
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getBeerByUpc(final String upc) {
        return beerMapper.mapToBeerDto(beerRepository.findByUpc(upc));
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
