package com.example._exer_json.car_dealer.services.impl;

import com.example._exer_json.car_dealer.models.dto.CustomerSeedDto;
import com.example._exer_json.car_dealer.models.entity.Customer;
import com.example._exer_json.car_dealer.repositories.CustomerRepository;
import com.example._exer_json.car_dealer.services.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMERS_FILE_NAME = "customers.json";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomers() throws IOException {
        if (customerRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + CUSTOMERS_FILE_NAME));

        CustomerSeedDto[] customerSeedDtos = gson
                .fromJson(fileContent, CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .filter(validationUtil::isValid)
                .map(CustomerSeedDto -> modelMapper.map(CustomerSeedDto, Customer.class))
                .forEach(customerRepository::save);
    }
}
