package be.helb_prigogine.game_manager.dto;


import be.helb_prigogine.game_manager.entities.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameDTO {
    //private Long id;  // We need the id to know which game to update but not sure ....
    private Integer maximumScore;  
    private GameType gameType;  
}

