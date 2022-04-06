package com.example._exer_json.car_dealer.services.impl;

import com.example._exer_json.car_dealer.models.dto.CarSeedDto;
import com.example._exer_json.car_dealer.models.entity.Car;
import com.example._exer_json.car_dealer.repositories.CarRepository;
import com.example._exer_json.car_dealer.services.CarService;
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
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_NAME = "cars.json";

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCars() throws IOException {
        if (carRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + CARS_FILE_NAME));

        CarSeedDto[] carSeedDtos = gson
                .fromJson(fileContent, CarSeedDto[].class);

        Arrays.stream(carSeedDtos)
                .filter(validationUtil::isValid)
                .map(CarSeedDto -> modelMapper.map(CarSeedDto, Car.class))
                .forEach(carRepository::save);

    }
}
