package com.example._xml_exer.service;

import com.example._xml_exer.model.dto.UserSeedDto;
import com.example._xml_exer.model.dto.UserViewRootDto;
import com.example._xml_exer.model.dto.UsersAndProductsViewRootDto;
import com.example._xml_exer.model.entity.User;

import java.util.List;

public interface UserService {

    long getEntityCount();

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    UserViewRootDto findUsersWithMoreThanOneSoldProduct();

    UsersAndProductsViewRootDto findUsersWithMoreThanOneSoldProductOrderByProductSoldDescThenByLastName();
}
