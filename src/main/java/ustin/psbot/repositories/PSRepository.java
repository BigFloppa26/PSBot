package ustin.psbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ustin.psbot.models.Game;

import java.util.Optional;

@Repository
public interface PSRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByNameOfTheGame(String nameOfTheGame);
    void deleteByNameOfTheGame(String name);
}
