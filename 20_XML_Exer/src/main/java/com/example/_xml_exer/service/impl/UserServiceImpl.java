package com.example._xml_exer.service.impl;

import com.example._xml_exer.model.dto.*;
import com.example._xml_exer.model.entity.User;
import com.example._xml_exer.repository.UserRepository;
import com.example._xml_exer.service.UserService;
import com.example._xml_exer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);

        return userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public UserViewRootDto findUsersWithMoreThanOneSoldProduct() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();

        userViewRootDto.setUsersWithProducts(userRepository
                .findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                .collect(Collectors.toList()));

        return userViewRootDto;
    }

    @Override
    public UsersAndProductsViewRootDto findUsersWithMoreThanOneSoldProductOrderByProductSoldDescThenByLastName() {
        UsersAndProductsViewRootDto usersAndProductsViewRootDto = new UsersAndProductsViewRootDto();
        usersAndProductsViewRootDto.setUsers(userRepository.findAllUsersWithMoreThanOneSoldProductOrderBySoldProductsDescLastNameAsc()
                        .stream()
                        .map(user -> modelMapper.map(user, UserAndSoldProductsDto.class))
                        .collect(Collectors.toList()));

        return usersAndProductsViewRootDto;
    }

    @Override
    public long getEntityCount() {
        return userRepository.count();
    }
}
