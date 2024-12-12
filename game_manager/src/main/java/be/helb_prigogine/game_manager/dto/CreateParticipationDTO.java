package be.helb_prigogine.game_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateParticipationDTO {
    @NotNull(message="Game id should be a number")
    private Long idGame;
    @NotNull(message="Player id should be a number")
    private Long idPlayer;
    @NotNull(message = "Score should be a number")
    private Integer score;
    private boolean isWinner;
}
