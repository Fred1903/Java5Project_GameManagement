package be.helb_prigogine.game_manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import be.helb_prigogine.game_manager.dao.ParticipationDAO;
import be.helb_prigogine.game_manager.dto.CreateParticipationDTO;
import be.helb_prigogine.game_manager.dto.ParticipationDTO;
import be.helb_prigogine.game_manager.entities.Participation;
import be.helb_prigogine.game_manager.services.GameService;
import be.helb_prigogine.game_manager.services.ParticipationService;
import be.helb_prigogine.game_manager.services.PlayerWebClientService;

class ParticipationServiceTest {

    @InjectMocks
    private ParticipationService participationService;

    @Mock
    private PlayerWebClientService playerWebClient;

    @Mock
    private GameService gameService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ParticipationDAO participationDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParticipation_shouldCreateSuccessfully() {
        CreateParticipationDTO createParticipationDTO = new CreateParticipationDTO();
        createParticipationDTO.setIdGame(1L);
        createParticipationDTO.setIdPlayer(2L);
        createParticipationDTO.setScore(10);
        createParticipationDTO.setWinner(true);

        Participation participation = new Participation(null, 1L, 2L, 10, true);
        Participation savedParticipation = new Participation(1L, 1L, 2L, 10, true);
        ParticipationDTO participationDTO = new ParticipationDTO();
        participationDTO.setId(1L);
        participationDTO.setIdGame(1L);
        participationDTO.setIdPlayer(2L);
        participationDTO.setScore(10);
        participationDTO.setWinner(true);

        doNothing().when(gameService).checkIfGameExists(1L);
        doNothing().when(gameService).checkIfPlayerExists(2L);

        when(participationDAO.findParticipationByIdPlayerAndIdGame(2L, 1L)).thenReturn(Optional.empty());
        when(participationDAO.savParticipation(any(Participation.class))).thenReturn(savedParticipation);
        when(modelMapper.map(savedParticipation, ParticipationDTO.class)).thenReturn(participationDTO);

        ParticipationDTO result = participationService.createParticipation(createParticipationDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getIdGame());
        assertEquals(2L, result.getIdPlayer());
        verify(playerWebClient, times(1)).updatePlayerStatistics(2L, 10);
    }

    @Test
    void createParticipation_shouldThrowExceptionWhenGameDoesNotExist() {
        CreateParticipationDTO createParticipationDTO = new CreateParticipationDTO();
        createParticipationDTO.setIdGame(1L);
        createParticipationDTO.setIdPlayer(2L);

        doThrow(new IllegalArgumentException("Game does not exist")).when(gameService).checkIfGameExists(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> participationService.createParticipation(createParticipationDTO));
        assertEquals("Game does not exist", exception.getMessage());
    }

    @Test
    void createParticipation_shouldThrowExceptionWhenPlayerDoesNotExist() {
        CreateParticipationDTO createParticipationDTO = new CreateParticipationDTO();
        createParticipationDTO.setIdGame(1L);
        createParticipationDTO.setIdPlayer(2L);

        doNothing().when(gameService).checkIfGameExists(1L);
        doThrow(new IllegalArgumentException("Player does not exist")).when(gameService).checkIfPlayerExists(2L);

        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> participationService.createParticipation(createParticipationDTO));
        assertEquals("Player does not exist", exception.getMessage());
    }

    @Test
    void createParticipation_shouldThrowExceptionWhenParticipationAlreadyExists() {
        CreateParticipationDTO createParticipationDTO = new CreateParticipationDTO();
        createParticipationDTO.setIdGame(1L);
        createParticipationDTO.setIdPlayer(2L);

        Participation existingParticipation = new Participation(1L, 1L, 2L, 10, false);

        doNothing().when(gameService).checkIfGameExists(1L);
        doNothing().when(gameService).checkIfPlayerExists(2L);
        when(participationDAO.findParticipationByIdPlayerAndIdGame(2L, 1L)).thenReturn(Optional.of(existingParticipation));

        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> participationService.createParticipation(createParticipationDTO));
        assertEquals("Player with ID 2 is already participating in the game with ID 1", exception.getMessage());
    }
}
