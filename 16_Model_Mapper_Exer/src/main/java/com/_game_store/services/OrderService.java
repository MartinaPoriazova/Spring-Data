package com._game_store.services;

import com._game_store.models.entities.Game;
import com._game_store.models.entities.User;

import java.util.Set;

public interface OrderService {

    String addToOrder(Game game, User user);

    String removeFromOrder(Game game, User user);

    Set<Game> buyItems(User user);
}
