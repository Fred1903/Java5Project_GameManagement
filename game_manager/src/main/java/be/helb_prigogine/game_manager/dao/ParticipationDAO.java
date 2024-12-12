package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import be.helb_prigogine.game_manager.entities.Participation;
import be.helb_prigogine.game_manager.repositories.ParticipationRepository;

@Repository
public class ParticipationDAO implements IParticipationDAO {
    private ParticipationRepository participationRepository;
    
    public ParticipationDAO(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    @Override
    public Participation savParticipation(Participation participation) {
        return participationRepository.save(participation);
    }

    @Override
    public Optional<Participation> findParticipationById(Long id) {
        return participationRepository.findById(id);
    }

    @Override
    public void deleteParticipation(Participation participation) {
        participationRepository.delete(participation);
    }

    @Override
    public Optional<Participation> findParticipationByIdPlayerAndIdGame(Long idPlayer, Long idGame) {
        return participationRepository.findByIdPlayerAndIdGame(idPlayer, idGame);
    }
    
}
