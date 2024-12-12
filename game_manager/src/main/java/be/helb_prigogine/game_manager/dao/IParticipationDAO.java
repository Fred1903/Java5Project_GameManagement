package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import be.helb_prigogine.game_manager.entities.Participation;

public interface IParticipationDAO {
    Participation savParticipation(Participation participation);
    Optional<Participation> findParticipationById(Long id);
    Optional<Participation> findParticipationByIdPlayerAndIdGame(Long idPlayer, Long idGame);
    void deleteParticipation(Participation participation);
}
