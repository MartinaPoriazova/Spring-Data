package com._game_store.services.impl;

import com._game_store.models.entities.Game;
import com._game_store.models.entities.dto.GameAddDTO;
import com._game_store.models.entities.dto.GameDetailsDTO;
import com._game_store.models.entities.dto.GameViewDTO;
import com._game_store.repositories.GameRepository;
import com._game_store.services.GameService;
import com._game_store.services.UserService;
import com._game_store.utl.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDTO gameAddDTO) {
        Set<ConstraintViolation<GameAddDTO>> violations = validationUtil.getViolations(gameAddDTO);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        Game game = modelMapper.map(gameAddDTO, Game.class);

        gameRepository.save(game);
        System.out.println("Added " + gameAddDTO.getTitle());
    }

    @Override
    public void editGame(Integer gameId, BigDecimal price, Float size) {
        Game game = gameRepository
                .findById(gameId)
                .orElse(null);

        if (game == null) {
            System.out.println("Incorrect id");
            return;
        }
        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void deleteGame(Integer gameId) {
        Game game = gameRepository
                .findById(gameId)
                .orElse(null);

        if (game == null) {
            System.out.println("Unexisting id");
            return;
        }

        gameRepository.delete(game);
        System.out.println("Deleted " + game.getTitle());
    }

    @Override
    public void getAllGames() {
        gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, GameViewDTO.class))
                .map(GameViewDTO::toString)
                .forEach(System.out::println);
    }

    @Override
    public void detailGame(String title) {
        Game game = gameRepository
                .findOneByTitle(title)
                .orElse(null);

        if (game == null) {
            System.out.println("Game " + title + " not found");
            return;
        }

        String detailGame = modelMapper.map(game, GameDetailsDTO.class).toString();
        System.out.println(detailGame);
    }

    @Override
    public void ownedGames() {

//        String ownedGames = this.userService.getCurrentUser()
//                .getGames().stream()
//                .map(Game::getTitle)
//                .collect(Collectors.joining(System.lineSeparator()));
//
//        System.out.println(ownedGames);
    }



}