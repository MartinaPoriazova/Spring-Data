package com.example._exer_json.services;

import com.example._exer_json.models.dto.UserSoldDTO;
import com.example._exer_json.models.dto.UserWithMinOneSoldProductDTO;
import com.example._exer_json.models.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDTO> findAllUsersWithMoreThanOneSoldProducts();

    List<UserWithMinOneSoldProductDTO> findAllUsersWithMinOneSoldProduct();
}
