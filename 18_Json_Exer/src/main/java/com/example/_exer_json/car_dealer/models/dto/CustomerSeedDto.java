package com.example._exer_json.car_dealer.models.dto;

import com.google.gson.annotations.Expose;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;

public class CustomerSeedDto {
    @Expose
    @Size(min = 1)
    private String name;
    @Expose
    @NonNull
    private String birthDate;
    @Expose
    @NonNull
    private Boolean isYoungDriver;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
