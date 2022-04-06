package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedDto;
import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownService townService, TeamService teamService, StatService statService) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser
                .fromFile(PLAYERS_FILE_PATH, PlayerSeedRootDto.class)
                .getPlayers()
                .stream()
                .filter(playerSeedDto -> {
                    boolean isValid = validationUtil.isValid(playerSeedDto);

                    if (exists(playerSeedDto)) {
                        isValid = false;
                    }

                    stringBuilder
                            .append(isValid
                                    ? String.format("Successfully imported Player %s %s - %s",
                                    playerSeedDto.getFirstName(),
                                    playerSeedDto.getLastName(),
                                    playerSeedDto.getPosition())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setTown(townService.getTownByName(player.getTown().getName()));
                    player.setTeam(teamService.getTeamByName(player.getTeam().getName()));
                    player.setStat(statService.getStatById(player.getStat().getId()));
                    return player;
                })
                .forEach(playerRepository::save);

        return stringBuilder.toString().trim();
    }

    private boolean exists(PlayerSeedDto playerSeedDto) {
        return playerRepository.existsByEmail(playerSeedDto.getEmail());
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder stringBuilder = new StringBuilder();
        playerRepository
                .findBestPlayersBornBetween(LocalDate.of(1995, 01, 01), LocalDate.of(2003, 01, 01))
                .forEach(player -> stringBuilder
                        .append(String.format("Player - %s %s\n" +
                                        "\tPosition - %s\n" +
                                        "Team - %s\n" +
                                        "\tStadium - %s\n",
                                player.getFirstName(), player.getLastName(),
                                player.getPosition(),
                                player.getTeam().getName(),
                                player.getTeam().getStadiumName()
                                ))
                        .append(System.lineSeparator())
                );
        return stringBuilder.toString().trim();
    }
}
