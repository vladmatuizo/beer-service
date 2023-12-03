package com.example.beerservice.web.controller;

import com.example.beerservice.service.BeerService;
import com.example.beerservice.web.model.BeerDto;
import com.example.beerservice.web.model.BeerStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "beer.service.host", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    final ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

    BeerDto validBeer;

    @BeforeEach
    public void setUp() {
        this.validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .beerName("Beer1")
                .beerStyle(BeerStyle.LAGER)
                .upc("1123213112312")
                .price(BigDecimal.ONE)
                .quantityOnHand(1)
                .build();
    }

    private static RestDocumentationResultHandler createGetBeerByIdDocument() {
        return document("v1/beer-get",
                pathParameters(
                        parameterWithName("id").description("UUID of desired beer to get.")
                ),
                queryParameters(
                        parameterWithName("isCold").description("Is beer cold query parameter (for demo purpose).")
                ),
                responseFields(
                        fieldWithPath("id").description("Id of the beer"),
                        fieldWithPath("version").description("Version number"),
                        fieldWithPath("createdDate").description("Date Created"),
                        fieldWithPath("lastModifiedDate").description("Last Modified date"),
                        fieldWithPath("beerName").description("Beer Name"),
                        fieldWithPath("beerStyle").description("Beer Style"),
                        fieldWithPath("upc").description("UPC of Beer"),
                        fieldWithPath("price").description("Price"),
                        fieldWithPath("quantityOnHand").description("Quantity On Hand")
                )
        );
    }

    @Test
    void getBeer() throws Exception {

        given(beerService.getBeerById(any(), anyBoolean())).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/{id}", UUID.randomUUID())
                        // param for docs demonstration purpose
                        .param("isCold", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(createGetBeerByIdDocument());
    }

    private static RestDocumentationResultHandler createCreateBeerDocument(final ConstrainedFields fields) {
        return document("v1/beer-create",
                requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Beer Name"),
                        fields.withPath("beerStyle").description("Beer Style"),
                        fields.withPath("upc").description("UPC of Beer").attributes(),
                        fields.withPath("price").description("Price"),
                        fields.withPath("quantityOnHand").ignored()
                )
        );
    }

    @Test
    void createBeer() throws Exception {
        final BeerDto beerDto = validBeer;
        beerDto.setId(null);
        beerDto.setVersion(null);
        beerDto.setCreatedDate(null);
        beerDto.setLastModifiedDate(null);

        final String json = objectMapper.writeValueAsString(beerDto);

        final BeerDto savedDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .upc("1123213112312")
                .price(BigDecimal.ONE)
                .build();
        given(beerService.create(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(createCreateBeerDocument(fields));
    }

    private static RestDocumentationResultHandler createUpdateBeerDocument(final ConstrainedFields fields) {
        return document("v1/beer-update",
                requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Beer Name"),
                        fields.withPath("beerStyle").description("Beer Style"),
                        fields.withPath("upc").description("UPC of Beer").attributes(),
                        fields.withPath("price").description("Price"),
                        fields.withPath("quantityOnHand").ignored()
                )
        );
    }

    @Test
    void updateBeer() throws Exception {
        final BeerDto beerDto = BeerDto.builder()
                .beerName("Beer1")
                .upc("1123213112312")
                .price(BigDecimal.ONE)
                .build();
        final String json = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent())
                .andDo(createUpdateBeerDocument(fields));
    }
}