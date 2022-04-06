package com.example._exer_json.car_dealer.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CarSeedDto {
    @Expose
    @Size(min = 1)
    private String make;
    @Expose
    @Size(min = 1)
    private String model;
    @Expose
    @Min(0)
    @Max(Long.MAX_VALUE)
    private long travelledDistance;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
