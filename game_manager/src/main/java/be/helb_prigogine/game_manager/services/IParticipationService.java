package be.helb_prigogine.game_manager.services;

import be.helb_prigogine.game_manager.dto.CreateParticipationDTO;
import be.helb_prigogine.game_manager.dto.ParticipationDTO;

public interface IParticipationService {
    ParticipationDTO createParticipation(CreateParticipationDTO createParticipationDTO);
}
