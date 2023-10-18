package com.example.beerservice.web.mapper;

import com.example.beerservice.domain.Beer;
import com.example.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    Beer mapToBeer(BeerDto beerDto);
    BeerDto mapToBeerDto(Beer beer);
}
