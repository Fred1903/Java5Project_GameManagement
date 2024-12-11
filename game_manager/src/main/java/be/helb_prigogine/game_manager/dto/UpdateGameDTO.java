package be.helb_prigogine.game_manager.dto;


import java.util.Arrays;

import be.helb_prigogine.game_manager.entities.GameType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameDTO {
    @NotNull(message="Maximum Score should be a number")
    private Integer maximumScore;  
    @NotNull(message="Game type should not be blank : ")
    @Enumerated(EnumType.STRING)
    private GameType gameType;  
}

