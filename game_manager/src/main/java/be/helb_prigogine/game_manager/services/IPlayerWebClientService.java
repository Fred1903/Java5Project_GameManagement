package be.helb_prigogine.game_manager.services;

public interface IPlayerWebClientService {
    boolean isPlayerExisting(Long idPlayer);
    void updatePlayerStatistics(Long idPlayer, Integer points);
}
