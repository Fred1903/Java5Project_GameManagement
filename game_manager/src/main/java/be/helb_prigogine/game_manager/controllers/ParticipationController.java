package be.helb_prigogine.game_manager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.helb_prigogine.game_manager.dto.CreateGameDTO;
import be.helb_prigogine.game_manager.dto.CreateParticipationDTO;
import be.helb_prigogine.game_manager.dto.GameDTO;
import be.helb_prigogine.game_manager.dto.ParticipationDTO;
import be.helb_prigogine.game_manager.services.ParticipationService;
import be.helb_prigogine.game_manager.utils.ValidationUtils;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/participations")
@Validated
public class ParticipationController {
    private final ParticipationService participationService;
    private final ValidationUtils validationUtils;
    
    public ParticipationController(ParticipationService participationService,ValidationUtils validationUtils) {
        this.participationService = participationService;
        this.validationUtils=validationUtils;
    }

    @PostMapping("create")
    public ResponseEntity<?> createParticipation(@Valid @RequestBody CreateParticipationDTO createParticipationDTO, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationUtils.getErrorMessage(bindingResult));
            }
            ParticipationDTO participationDTO = participationService.createParticipation(createParticipationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(participationDTO);
        } 
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating the participation, "+e.getMessage());
        }
    }

}
