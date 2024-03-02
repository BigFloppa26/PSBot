package ustin.psbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ustin.psbot.models.Game;

import java.util.List;
import java.util.Optional;

@Repository
public interface PSRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByNameOfTheGame(String nameOfTheGame);
    void deleteByNameOfTheGame(String name);

    Game findGamesByNameOfTheGame(String name);

    List<Game> findGamesByPsPlatformsIgnoreCaseAndLocationsIgnoreCaseAndStyleIgnoreCase(String platforms, String locations, String style);

    void deleteById(int id);
}
