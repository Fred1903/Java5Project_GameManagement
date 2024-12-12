package be.helb_prigogine.game_manager.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import be.helb_prigogine.game_manager.dao.GameDAO;
import be.helb_prigogine.game_manager.dto.CreateGameDTO;
import be.helb_prigogine.game_manager.dto.CreateParticipationDTO;
import be.helb_prigogine.game_manager.dto.GameDTO;
import be.helb_prigogine.game_manager.dto.ParticipationDTO;
import be.helb_prigogine.game_manager.dto.UpdateGameDTO;
import be.helb_prigogine.game_manager.entities.Game;
import be.helb_prigogine.game_manager.entities.GameType;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Service
public class GameService implements IGameService {
    private final ModelMapper modelMapper;
    private final GameDAO gameDAO;
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private final PlayerWebClientService playerWebClient;

    public GameService(ModelMapper modelMapper, GameDAO gameDAO, PlayerWebClientService playerWebClient) {
        this.modelMapper=modelMapper;
        this.gameDAO=gameDAO;
        this.playerWebClient=playerWebClient;
    }


    @Transactional
    @Override
    public GameDTO createGame(CreateGameDTO createGameDTO) { 
        boolean isGameTypeValid = Arrays.stream(GameType.values()).anyMatch(gameType -> gameType.name().equals(createGameDTO.getGameType().toString()));
        if (!isGameTypeValid)throw new IllegalArgumentException("The game type has to be one of the following : " + Arrays.toString(GameType.values()));
        //if (!playerWebClient.isPlayerExisting(createGameDTO.getIdHost()))throw new IllegalArgumentException("Host ID does not exist in the Player service.");
        checkIfPlayerExists(createGameDTO.getIdHost());

        Game game = modelMapper.map(createGameDTO, Game.class);
        game.setId(null); //Pour une raison que j'ignore, ca a mis une valeur à l'id donc on remet null sinon ca créé une erreur
        game.setGameDate(CURRENT_DATE);
        Game savedGame = gameDAO.saveGame(game);

        //When creating a game, we don't create a participation because it will make a circular array and create dependencies, instead
        //you have to make a new call to add a participation

        return modelMapper.map(savedGame, GameDTO.class);
    }

    void checkIfPlayerExists(Long idPlayer){
        if (!playerWebClient.isPlayerExisting(idPlayer)){
            throw new IllegalArgumentException("There is no player with this id : "+idPlayer);
        }
    }

    @Override
    public GameDTO updateGame(Long idGame, UpdateGameDTO updateGame) {
        checkIfGameExists(idGame);
        if(updateGame.getGameType()==null || updateGame.getMaximumScore()==null){
            throw new RuntimeException("Plese provide a Game Type or Maximum Score to update");
        }
        Game game = gameDAO.findGameById(idGame).get();
        if(updateGame.getGameType()!=null)game.setGameType(updateGame.getGameType());
        if(updateGame.getMaximumScore()!=null)game.setMaximumScore(updateGame.getMaximumScore());
        gameDAO.saveGame(game);  
        return modelMapper.map(game,GameDTO.class);
    }   

    void checkIfGameExists(Long idGame){
        if(!gameDAO.findGameById(idGame).isPresent()){
            throw new RuntimeException("Game with ID " + idGame + " does not exist");
        }
    }


    @Override
    public void deleteGame(Long idGame) {
        checkIfGameExists(idGame);
        gameDAO.deleteGame(idGame);
    }


    @Override
    public GameDTO getGameInformations(Long idGame) {
        /*checkIfGameExists(idGame);
        Optional<Game> gameToReturn = gameDAO.findGameById(idGame); //optional sert a rien ici mais est utilisé dans el checkifgameexit
        return modelMapper.map(gameToReturn,GameDTO.class);*/ 
        //The problem with this code is that in checkIfGameExists it wil use findGameById and then here from new, so we duplicate 
        //the info and are making 2 times SQL requests, instead we could just make :
        Game gameToReturn = gameDAO.findGameById(idGame)
            .orElseThrow(() -> new RuntimeException("Game with ID " + idGame + " does not exist"));
            return modelMapper.map(gameToReturn,GameDTO.class);
    }
}
