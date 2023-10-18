package com.example.beerservice.web.controller;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

class ConstrainedFields {

    private final ConstraintDescriptions constraintDescriptions;

    ConstrainedFields(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    FieldDescriptor withPath(String path) {
        return fieldWithPath(path).attributes(key("constraints")
                .value(StringUtils.collectionToDelimitedString(
                        this.constraintDescriptions.descriptionsForProperty(path), ". "
                )));
    }
}
