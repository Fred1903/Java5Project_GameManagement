package be.helb_prigogine.game_manager.dto;


import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter 
public class ParticipationDTO {
    //private Long id;  Do we need the id to create a participation ? No so we will see
    private Long idGame;
    private Long idPlayer;
    private int score;
    private boolean isWinner;
}
