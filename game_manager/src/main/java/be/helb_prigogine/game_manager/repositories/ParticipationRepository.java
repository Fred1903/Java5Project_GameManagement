package be.helb_prigogine.game_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.helb_prigogine.game_manager.entities.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    //CRUD sql will be made automatically but if we want to add special sql queries they will be here
}
