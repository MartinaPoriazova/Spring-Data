package com.example._exer_json.car_dealer.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class SuppliedSeedDto {
    @Expose
    @Size(min=1)
    private String name;
    @Expose
    private boolean isImporter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
