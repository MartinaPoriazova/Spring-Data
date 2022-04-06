package com._game_store.services;

import com._game_store.models.entities.dto.GameAddDTO;

import java.math.BigDecimal;

public interface GameService {

    void addGame(GameAddDTO gameAddDTO);

    void editGame(Integer gameId, BigDecimal price, Float size);

    void deleteGame(Integer gameId);

    void getAllGames();

    void detailGame(String title);

    void ownedGames();
}
