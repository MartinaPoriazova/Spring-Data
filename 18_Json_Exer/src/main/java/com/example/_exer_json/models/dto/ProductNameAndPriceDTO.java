package com.example._exer_json.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductNameAndPriceDTO {

    @Expose
    private String name;

    @Expose
    @Positive
    private BigDecimal price;

    @Expose
    private String seller;

    public ProductNameAndPriceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}