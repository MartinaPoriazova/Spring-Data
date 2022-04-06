package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByEmail(String email);

//    @Query("SELECT p FROM players p WHERE p.birthDate BETWEEN :after AND :before" +
//            " ORDER BY p.stat.shooting DESC, p.stat.passing DESC, p.stat.endurance DESC, p.lastName")

    @Query("SELECT p " +
            "FROM Player p JOIN p.stat s " +
            "JOIN p.team t " +
            "WHERE p.birthDate BETWEEN :begin AND :end " +
            "ORDER BY s.shooting DESC, s.passing DESC, s.endurance DESC, p.lastName")
    List<Player> findBestPlayersBornBetween(LocalDate begin, LocalDate end);
}
