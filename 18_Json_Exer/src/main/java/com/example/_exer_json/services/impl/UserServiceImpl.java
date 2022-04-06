package com.example._exer_json.services.impl;

import com.example._exer_json.constants.GlobalConstant;
import com.example._exer_json.models.dto.UserSeedDTO;
import com.example._exer_json.models.dto.UserSoldDTO;
import com.example._exer_json.models.dto.UserWithMinOneSoldProductDTO;
import com.example._exer_json.models.entities.User;
import com.example._exer_json.repositories.UserRepository;
import com.example._exer_json.services.UserService;
import com.example._exer_json.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                            Files.readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + USERS_FILE_NAME)),
                            UserSeedDTO[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
                    .forEach(userRepository::save);
        }
//        String fileContent = Files
//                .readString(Path.of(GlobalConstant.RESOURCE_FILE_PATH + USERS_FILE_NAME));
//
//        UserSeedDTO[] UserSeedDTOS = gson
//                .fromJson(fileContent, UserSeedDTO[].class);
//
//        Arrays.stream(UserSeedDTOS)
//                .filter(validationUtil::isValid)
//                .map(UserSeedDTO -> modelMapper.map(UserSeedDTO , User.class))
//                .forEach(userRepository::save);
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);

        return userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<UserSoldDTO> findAllUsersWithMoreThanOneSoldProducts() {
        return userRepository
                .findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWithMinOneSoldProductDTO> findAllUsersWithMinOneSoldProduct() {
        return userRepository
                .findAllUsersWithMinOneSoldProductOrderByLastNameThenFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserWithMinOneSoldProductDTO.class))
                .collect(Collectors.toList());
    }
}
