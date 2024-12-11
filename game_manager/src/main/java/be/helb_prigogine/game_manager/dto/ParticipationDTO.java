package be.helb_prigogine.game_manager.dto;


import lombok.Getter;
import lombok.Setter;

//DTO is there to 'encapsulate' the data of the entities, and we give juste the data that we need

@Getter 
@Setter 
public class ParticipationDTO {
    //private Long id;  Do we need the id to create a participation ? No so we will see
    private Long idGame;
    private Long idPlayer;
    private Integer score;
    private boolean isWinner;
}