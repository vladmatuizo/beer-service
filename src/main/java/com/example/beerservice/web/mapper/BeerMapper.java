package com.example.beerservice.web.mapper;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    Beer mapToBeer(BeerDto beerDto);
    BeerDto mapToBeerDtoWithInventory(Beer beer);
    BeerDto mapToBeerDto(Beer beer);
}
