package be.helb_prigogine.game_manager.entities;

//Jakarta Persistence nécessaire à importer pour pouvoir use les @ suivants :
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate; 

//Lombok nécessaire pour pouvoir use les @ suivants :
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity //so we can use jpa for @Id
@AllArgsConstructor //creates a ctor for the class with all fields
@NoArgsConstructor  //and one empty
@Getter //Getter ans setter for all fields
@Setter
@Table(name = "game", schema = "java5project")
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private LocalDate gameDate;
    private Integer maximumScore;
    @Enumerated(EnumType.STRING)
    private GameType gameType;
    private Long idHost;
}
