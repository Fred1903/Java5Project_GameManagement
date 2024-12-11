package be.helb_prigogine.game_manager.entities;

//Jakarta Persistence nécessaire à importer pour pouvoir use les @ suivants :
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


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
@Table(name = "participation", schema = "java5project")
public class Participation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long idGame;
    private Long idPlayer;
    private Integer score;
    private boolean isWinner;    
}
