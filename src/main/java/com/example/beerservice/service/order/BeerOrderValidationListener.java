package com.example.beerservice.service.order;

import com.example.common.model.BeerOrderDto;
import com.example.common.model.event.ValidateOrderRequest;
import com.example.common.model.event.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.example.beerservice.config.MessagingConfig.VALIDATE_ORDER_QUEUE_NAME;
import static com.example.beerservice.config.MessagingConfig.VALIDATE_ORDER_RESPONSE_QUEUE_NAME;

@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = VALIDATE_ORDER_QUEUE_NAME)
    public void listen(ValidateOrderRequest validateOrderRequest) {
        BeerOrderDto beerOrder = validateOrderRequest.beerOrder();
        boolean isValid = beerOrderValidator.validate(beerOrder);

        jmsTemplate.convertAndSend(VALIDATE_ORDER_RESPONSE_QUEUE_NAME,
                new ValidateOrderResult(beerOrder.getId(), isValid)
        );
    }
}
