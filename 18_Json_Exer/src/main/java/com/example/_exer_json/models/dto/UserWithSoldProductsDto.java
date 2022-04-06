package com.example._exer_json.models.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserWithSoldProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private Set<SoldProductsDTO> soldProducts;

    public UserWithSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<SoldProductsDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<SoldProductsDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}