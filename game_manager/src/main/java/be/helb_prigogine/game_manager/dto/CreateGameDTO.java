package be.helb_prigogine.game_manager.dto;

import be.helb_prigogine.game_manager.entities.GameType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGameDTO {
    @NotNull(message="Maximum Score should be a number")
    private Integer maximumScore;
    @NotNull(message="Game type should not be blank")
    @Enumerated(EnumType.STRING)
    private GameType gameType;
    @NotNull(message="Host id should not be blank")
    private Long idHost;
}
