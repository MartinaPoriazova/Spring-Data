package com.example._xml_exer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryViewRootDto {

    @XmlElement(name = "category")
    List<CategoryWithProductsStatDto> categories;

    public List<CategoryWithProductsStatDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryWithProductsStatDto> categories) {
        this.categories = categories;
    }
}
