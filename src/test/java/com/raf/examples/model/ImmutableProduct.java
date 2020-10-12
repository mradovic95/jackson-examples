package com.raf.examples.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ImmutableProduct {

    private final String title;
    private final String description;
    private final BigDecimal price;

    @JsonCreator
    public ImmutableProduct(@JsonProperty("title") String title, @JsonProperty("description") String description,
                            @JsonProperty("price") BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public ImmutableProduct(@JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
        this.price = BigDecimal.ONE;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
