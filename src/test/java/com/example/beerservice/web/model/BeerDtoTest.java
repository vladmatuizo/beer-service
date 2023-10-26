package com.example.beerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JsonTest
class BeerDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto validBeer;

    @BeforeEach
    public void setUp() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        final OffsetDateTime dateTime = OffsetDateTime.parse("2023-10-19T00:02:02+0200", dateTimeFormatter)
                .withOffsetSameInstant(ZoneOffset.UTC);
        this.validBeer = BeerDto.builder()
                .id(UUID.fromString("d4120465-4d90-4d20-9196-f371acb61dcb"))
                .version(1)
                .createdDate(dateTime)
                .lastModifiedDate(dateTime)
                .beerName("Beer1")
                .beerStyle(BeerStyle.LAGER)
                .upc("1123213112312")
                .price(BigDecimal.ONE)
                .quantityOnHand(1)
                .build();
    }

    @Test
    public void testSerializeDto() throws JsonProcessingException {
        final BeerDto beerDto = validBeer;

        final String json = objectMapper.writeValueAsString(beerDto);

        System.out.println(json);
        assertFalse(json.isEmpty());
    }

    @Test
    public void testDeserializeDto() throws JsonProcessingException {
        final String beerJson = """
                {
                    "id":"d4120465-4d90-4d20-9196-f371acb61dcb",
                    "version":1,
                    "createdDate":"2023-10-19T00:02:02+0200",
                    "lastModifiedDate":"2023-10-19T00:02:02+0200",
                    "beerName":"Beer1",
                    "beerStyle":"LAGER",
                    "upc":1123213112312,
                    "price":"1",
                    "quantityOnHand":1
                }""";

        final BeerDto dto = objectMapper.readValue(beerJson, BeerDto.class);

        System.out.println(dto);
        assertEquals(validBeer, dto);
    }
}