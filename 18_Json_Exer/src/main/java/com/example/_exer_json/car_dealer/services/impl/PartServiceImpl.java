package com.example._exer_json.car_dealer.services.impl;

import com.example._exer_json.car_dealer.models.dto.PartSeedDto;
import com.example._exer_json.car_dealer.models.entity.Part;
import com.example._exer_json.car_dealer.repositories.PartRepository;
import com.example._exer_json.car_dealer.services.PartService;
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
public class PartServiceImpl implements PartService {

    private static final String PARTS_FILE_NAME = "parts.json";

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedParts() throws IOException {
        if (partRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + PARTS_FILE_NAME));

        PartSeedDto[] partSeedDtos = gson
                .fromJson(fileContent, PartSeedDto[].class);

        Arrays.stream(partSeedDtos)
                .filter(validationUtil::isValid)
                .map(PartSeedDto -> modelMapper.map(PartSeedDto, Part.class))
                .forEach(partRepository::save);
    }
}
