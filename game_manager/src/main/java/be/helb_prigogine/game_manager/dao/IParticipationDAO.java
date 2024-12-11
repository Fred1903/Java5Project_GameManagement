package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import be.helb_prigogine.game_manager.entities.Participation;

public interface IParticipationDAO {
    Participation savParticipation(Participation participation);
    boolean isParticipationExisting(Long id);
    Optional<Participation> findParticipation(Long id);
    void deleteParticipation(Participation participation);
}
