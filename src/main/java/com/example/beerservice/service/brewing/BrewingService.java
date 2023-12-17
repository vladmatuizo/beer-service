package com.example.beerservice.service.brewing;

import com.example.beerservice.domain.Beer;
import com.example.common.model.event.BrewBeerEvent;
import com.example.beerservice.repository.BeerRepository;
import com.example.beerservice.service.inventory.BeerInventoryService;
import com.example.beerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.beerservice.config.MessagingConfig.BREWING_REQUEST_QUEUE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer onHandInventory = beerInventoryService.getOnHandInventory(beer.getId());


            Integer minOnHand = beer.getMinOnHand();
            log.debug("Min on hand for bear is {}", minOnHand);
            log.debug("On hand inventory is {}", onHandInventory);
            if (minOnHand >= onHandInventory) {
                jmsTemplate.convertAndSend(BREWING_REQUEST_QUEUE_NAME,
                        new BrewBeerEvent(beerMapper.mapToBeerDto(beer)));
            }
        });
    }
}
