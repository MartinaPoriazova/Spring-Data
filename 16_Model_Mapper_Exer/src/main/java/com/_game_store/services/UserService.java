package com._game_store.services;

import com._game_store.models.entities.User;
import com._game_store.models.entities.dto.UserLoginDTO;
import com._game_store.models.entities.dto.UserRegisterDTO;


public interface UserService {

    void registerUser(UserRegisterDTO userRegisterDTO);

    void loginUser(UserLoginDTO userLoginDTO);

    void logout();

}
