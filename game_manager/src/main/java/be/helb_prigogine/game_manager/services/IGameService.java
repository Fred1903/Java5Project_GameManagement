package be.helb_prigogine.game_manager.services;

import be.helb_prigogine.game_manager.dto.CreateGameDTO;
import be.helb_prigogine.game_manager.dto.GameDTO;
import be.helb_prigogine.game_manager.dto.UpdateGameDTO;

public interface IGameService {
    GameDTO createGame(CreateGameDTO createGameDTO);
    GameDTO updateGame(Long idGame, UpdateGameDTO updateGame);
    void deleteGame(Long idGame);
    GameDTO getGameInformations(Long idGame);
}
