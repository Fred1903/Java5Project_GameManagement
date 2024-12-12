package be.helb_prigogine.game_manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import be.helb_prigogine.game_manager.dao.GameDAO;
import be.helb_prigogine.game_manager.dto.CreateGameDTO;
import be.helb_prigogine.game_manager.dto.GameDTO;
import be.helb_prigogine.game_manager.entities.Game;
import be.helb_prigogine.game_manager.entities.GameType;
import be.helb_prigogine.game_manager.services.GameService;
import be.helb_prigogine.game_manager.services.PlayerWebClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GameDAO gameDAO;

    @Mock
    private PlayerWebClientService playerWebClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_success() {
        CreateGameDTO createGameDTO = new CreateGameDTO();
        createGameDTO.setGameType(GameType.DUO);
        createGameDTO.setMaximumScore(10);
        createGameDTO.setIdHost(1L);

        Game gameEntity = new Game();
        gameEntity.setGameType(GameType.DUO);
        gameEntity.setMaximumScore(10);
        gameEntity.setIdHost(1L);

        Game savedGame = new Game();
        savedGame.setId(1L);
        savedGame.setGameType(GameType.DUO);
        savedGame.setMaximumScore(10);
        savedGame.setIdHost(1L);

        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(1L);
        gameDTO.setGameType(GameType.DUO);
        gameDTO.setMaximumScore(10);
        gameDTO.setIdHost(1L);

        when(playerWebClient.isPlayerExisting(1L)).thenReturn(true);
        when(modelMapper.map(createGameDTO, Game.class)).thenReturn(gameEntity);
        when(gameDAO.saveGame(gameEntity)).thenReturn(savedGame);
        when(modelMapper.map(savedGame, GameDTO.class)).thenReturn(gameDTO);

        GameDTO result = gameService.createGame(createGameDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(playerWebClient, times(1)).isPlayerExisting(1L);
        verify(gameDAO, times(1)).saveGame(gameEntity);
    }

    @Test
    void createGame_invalidGameType() {
        CreateGameDTO createGameDTO = new CreateGameDTO();
        createGameDTO.setGameType(null);
        createGameDTO.setMaximumScore(10);
        createGameDTO.setIdHost(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(createGameDTO);
        });

        assertEquals("The game type has to be one of the following : [DUO, TRIO, SQUAD]", exception.getMessage());
        verifyNoInteractions(gameDAO, playerWebClient);
    }

    @Test
    void createGame_nonExistingHost() {
        CreateGameDTO createGameDTO = new CreateGameDTO();
        createGameDTO.setGameType(GameType.DUO);
        createGameDTO.setMaximumScore(10);
        createGameDTO.setIdHost(1L);

        when(playerWebClient.isPlayerExisting(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(createGameDTO);
        });

        assertEquals("There is no player with this id : 1", exception.getMessage());
        verify(playerWebClient, times(1)).isPlayerExisting(1L);
        verifyNoInteractions(gameDAO);
    }

    @Test
    void updateGame_gameNotFound() {
        when(gameDAO.findGameById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.updateGame(1L, null);
        });

        assertEquals("Game with ID 1 does not exist", exception.getMessage());
        verify(gameDAO, times(1)).findGameById(1L);
    }

    @Test
    void deleteGame_success() {
        Game game = new Game();
        game.setId(1L);

        when(gameDAO.findGameById(1L)).thenReturn(Optional.of(game));

        gameService.deleteGame(1L);

        verify(gameDAO, times(1)).deleteGame(1L);
    }

    @Test
    void deleteGame_gameNotFound() {
        when(gameDAO.findGameById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.deleteGame(1L);
        });

        assertEquals("Game with ID 1 does not exist", exception.getMessage());
        verify(gameDAO, times(1)).findGameById(1L);
        verifyNoMoreInteractions(gameDAO);
    }
}
