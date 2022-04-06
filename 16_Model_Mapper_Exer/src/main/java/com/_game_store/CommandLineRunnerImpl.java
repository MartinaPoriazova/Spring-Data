package com._game_store;

import com._game_store.models.entities.dto.GameAddDTO;
import com._game_store.models.entities.dto.UserLoginDTO;
import com._game_store.models.entities.dto.UserRegisterDTO;
import com._game_store.services.GameService;
import com._game_store.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter command: ");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser" -> userService
                        .registerUser(new UserRegisterDTO(commands[1], commands[2], commands[3], commands[4]));
                case "LoginUser" -> userService
                        .loginUser(new UserLoginDTO(commands[1], commands[2]));
                case "Logout" -> userService
                        .logout();
                case "AddGame" -> gameService
                        .addGame(new GameAddDTO(commands[1], new BigDecimal(commands[2]), Double.parseDouble(commands[3]),
                                commands[4], commands[5], commands[6],
                                LocalDate.parse(commands[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                case "EditGame" -> gameService
                        .editGame(Integer.parseInt(commands[1]),
                                new BigDecimal(commands[2].split("=")[1]),
                                Float.parseFloat(commands[3].split("=")[1]));
                case "DeleteGame" -> gameService
                        .deleteGame(Integer.parseInt(commands[1]));
                case "AllGames" -> gameService
                        .getAllGames();
                case "DetailGame" -> gameService
                        .detailGame(commands[1]);
                //TODO: dont try it, not ready :)
                case "OwnedGames" -> gameService
                        .ownedGames();

            }
        }
    }
}
