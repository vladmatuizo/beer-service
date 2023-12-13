package com.example.beerservice.web.mapper;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.service.inventory.BeerInventoryService;
import com.example.beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper delegate;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setMapper(BeerMapper delegate) {
        this.delegate = delegate;
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
