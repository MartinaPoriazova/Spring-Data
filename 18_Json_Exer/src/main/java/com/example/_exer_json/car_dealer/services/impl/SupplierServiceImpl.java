package com.example._exer_json.car_dealer.services.impl;

import com.example._exer_json.car_dealer.models.entity.Supplier;
import com.example._exer_json.car_dealer.repositories.SupplierRepository;
import com.example._exer_json.car_dealer.services.SupplierService;
import com.example._exer_json.constants.GlobalConstant;
import com.example._exer_json.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String SUPPLIERS_FILE_NAME = "suppliers.json";

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (supplierRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + SUPPLIERS_FILE_NAME));

        SupplierSeedDto[] supplierSeedDtos = gson
                .fromJson(fileContent, SupplierSeedDto[].class);

        Arrays.stream(supplierSeedDtos)
                .filter(validationUtil::isValid)
                .map(SupplierSeedDto -> modelMapper.map(SupplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);
    }
}

