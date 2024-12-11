package be.helb_prigogine.game_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.helb_prigogine.game_manager.entities.Game;

@Repository //Grace à bean on peut juste @
public interface GameRepository extends JpaRepository<Game,Long> {
    //CRUD sql will be made automatically but if we want to add special sql queries they will be here
}
