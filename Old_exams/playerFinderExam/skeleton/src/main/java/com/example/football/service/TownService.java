package com.example.football.service;

import com.example.football.models.entity.Town;

import java.io.IOException;
import java.util.Optional;

public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importTowns() throws IOException;

    Town findTownByName(String townName);

    Town getTownByName(String name);
}
