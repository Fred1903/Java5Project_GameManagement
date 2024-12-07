package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import be.helb_prigogine.game_manager.entities.Game;
import be.helb_prigogine.game_manager.repositories.GameRepository;

@Repository
public class GameDAO implements IGameDAO {
    private GameRepository gameRepository;

    public GameDAO(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    //The next methods are CRUD methods, this means that in the gameRepository we already have  save, findById,....
    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public boolean isGameExisting(Long id) {
        return gameRepository.existsById(id);
    }

    @Override
    public Optional<Game> findGame(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
    
}
