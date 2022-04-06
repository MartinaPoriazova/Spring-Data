package com.example._exer_json.models.dto;


import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SoldProductsDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public SoldProductsDTO() {
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
}