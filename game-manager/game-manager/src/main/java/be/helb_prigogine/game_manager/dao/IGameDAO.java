package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import be.helb_prigogine.game_manager.entities.Game;

public interface IGameDAO {

    Game saveGame(Game game);
    boolean isGameExisting(Long id);
    Optional<Game> findGame(Long id);
    void deleteGame(Long id);
}