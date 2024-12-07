package be.helb_prigogine.game_manager.dao;

import java.util.Optional;

import be.helb_prigogine.game_manager.entities.Participation;
import be.helb_prigogine.game_manager.repositories.ParticipationRepository;

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
    public boolean isParticipationExisting(Long id) {
        return participationRepository.existsById(id);
    }

    @Override
    public Optional<Participation> findParticipation(Long id) {
        return participationRepository.findById(id);
    }

    @Override
    public void deleteParticipation(Participation participation) {
        participationRepository.delete(participation);
    }
    
}
