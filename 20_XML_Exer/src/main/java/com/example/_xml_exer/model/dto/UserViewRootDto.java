package com.example._xml_exer.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {

    @XmlElement(name = "user")
    private List<UserWithProductsDto> usersWithProducts;

    public List<UserWithProductsDto> getUsersWithProducts() {
        return usersWithProducts;
    }

    public void setUsersWithProducts(List<UserWithProductsDto> usersWithProducts) {
        this.usersWithProducts = usersWithProducts;
    }
}
