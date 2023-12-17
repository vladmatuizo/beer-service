package com.example.beerservice.service.brewing;

import com.example.beerservice.domain.Beer;
import com.example.common.model.event.BrewBeerEvent;
import com.example.common.model.event.NewInventoryEvent;
import com.example.beerservice.repository.BeerRepository;
import com.example.common.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.beerservice.config.MessagingConfig.BREWING_REQUEST_QUEUE_NAME;
import static com.example.beerservice.config.MessagingConfig.NEW_INVENTORY_QUEUE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = BREWING_REQUEST_QUEUE_NAME)
    public void listenBrewBeerEvent(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        Beer beer = beerRepository.findById(beerDto.getId()).orElseThrow();
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        log.debug("Brewed beer: {} : quantity on hand: {}", beer.getMinOnHand(), beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(NEW_INVENTORY_QUEUE_NAME, new NewInventoryEvent(beerDto));
    }
}
