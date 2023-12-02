package com.example.beerservice.web.mapper;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.service.inventory.BeerInventoryService;
import com.example.beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper delegate;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.delegate = mapper;
    }

    @Override
    public Beer mapToBeer(final BeerDto beerDto) {
        return delegate.mapToBeer(beerDto);
    }

    @Override
    public BeerDto mapToBeerDtoWithInventory(final Beer beer) {
        BeerDto dto = delegate.mapToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public BeerDto mapToBeerDto(final Beer beer) {
        return delegate.mapToBeerDto(beer);
    }
}
