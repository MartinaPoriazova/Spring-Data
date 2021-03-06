package com.example._exer_json.car_dealer.models.entity;

import com.example._exer_json.models.entities.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "travelled_distance", nullable = false)
    private long travelledDistance;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Part>parts;

    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST)
    private Set<Sale> sales;

    public Car() {
        this.parts = new HashSet<>();
        this.sales = new HashSet<>();
    }

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

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
