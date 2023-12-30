package com.example.beerservice.service.order;

import com.example.beerservice.repository.BeerRepository;
import com.example.common.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;


    public boolean validate(BeerOrderDto beerOrder) {
        return beerOrder.getBeerOrderLines().stream()
                .allMatch(beerOrderLine -> beerRepository.existsByUpc(beerOrderLine.getUpc()));
    }
}
