package be.helb_prigogine.game_manager.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import be.helb_prigogine.game_manager.dao.ParticipationDAO;
import be.helb_prigogine.game_manager.dto.CreateParticipationDTO;
import be.helb_prigogine.game_manager.dto.ParticipationDTO;
import be.helb_prigogine.game_manager.entities.Participation;

@Service
public class ParticipationService implements IParticipationService{
    private final PlayerWebClientService playerWebClient;
    private final GameService gameService;//We can not depend from gameService so we have to call an intermediate
    private final ModelMapper modelMapper;
    private final ParticipationDAO participationDAO;
    
    public ParticipationService(PlayerWebClientService playerWebClient,GameService gameService,ModelMapper modelMapper,ParticipationDAO participationDAO) {
        this.playerWebClient = playerWebClient;
        this.gameService=gameService;
        this.participationDAO=participationDAO;
        this.modelMapper=modelMapper;
    }


    @Override //normally we should also check if the game type is duo or trio and number of already existing particpants for the game but i dont have time
    public ParticipationDTO createParticipation(CreateParticipationDTO createParticipationDTO) {
        
        gameService.checkIfGameExists(createParticipationDTO.getIdGame());
        gameService.checkIfPlayerExists(createParticipationDTO.getIdPlayer());

        Optional<Participation> existingParticipation = participationDAO.findParticipationByIdPlayerAndIdGame(
        createParticipationDTO.getIdPlayer(),createParticipationDTO.getIdGame());

        if (existingParticipation.isPresent()) {
            throw new IllegalArgumentException("Player with ID " + createParticipationDTO.getIdPlayer() +
                " is already participating in the game with ID " + createParticipationDTO.getIdGame());
        }
        
        //I do not use modelMapper here because it will create errors as it has to variables starting with 'id'
        Participation participation=new Participation(null, createParticipationDTO.getIdGame(), createParticipationDTO.getIdPlayer(),
        createParticipationDTO.getScore(), createParticipationDTO.isWinner());

        Participation savedParticipation = participationDAO.savParticipation(participation);

        playerWebClient.updatePlayerStatistics(savedParticipation.getIdPlayer(), savedParticipation.getScore());
        return modelMapper.map(savedParticipation, ParticipationDTO.class);
    }
}
