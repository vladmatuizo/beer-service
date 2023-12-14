package com.example.beerservice.event;

import com.example.beerservice.web.model.BeerDto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BeerEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = -1020902357931248340L;

    private final BeerDto beerDto;
}
