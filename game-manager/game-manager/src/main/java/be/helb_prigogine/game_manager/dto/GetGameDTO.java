package be.helb_prigogine.game_manager.dto;

import java.time.LocalDate;

import be.helb_prigogine.game_manager.entities.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetGameDTO {
    private LocalDate gameDate;
    private int maximumScore;
    private GameType gameType;
}
