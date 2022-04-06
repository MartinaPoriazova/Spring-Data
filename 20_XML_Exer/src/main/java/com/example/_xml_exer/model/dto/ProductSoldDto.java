package com.example._xml_exer.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSoldDto {

    @XmlAttribute(name="count")
    private int count;
    @XmlElement(name="product")
    private List<ProductDetailsDto> soldProducts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductDetailsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductDetailsDto> soldProducts) {
        this.soldProducts = soldProducts;
        this.count = soldProducts.size();
    }
}