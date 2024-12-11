    package be.helb_prigogine.game_manager.controllers;

    import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
    import org.springframework.validation.BindingResult;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import be.helb_prigogine.game_manager.dto.CreateGameDTO;
    import be.helb_prigogine.game_manager.dto.GameDTO;
import be.helb_prigogine.game_manager.dto.UpdateGameDTO;
import be.helb_prigogine.game_manager.services.GameService;
    import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



    @RestController
    @RequestMapping("/games")
    @Validated
    public class GameController {
        private final GameService gameService;

        public GameController(GameService gameService) {
            this.gameService = gameService;
        }

        @PostMapping("/create")
        public ResponseEntity<?> createGame(@Valid @RequestBody CreateGameDTO createGameDTO,BindingResult bindingResult) {
            try {
                if (bindingResult.hasErrors()) { 
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErroMessage(bindingResult));
                }
                // We can just make GameService.createGame but then we wont return the newGame in the HTTP response
                GameDTO newGame = gameService.createGame(createGameDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(newGame);
            }
            catch (IllegalArgumentException e) {
                // Handles logical errors (e.g., invalid host ID)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with the Game creation : "+e.getMessage());
            }
            
        }

        @PutMapping("update/{id}")
        public ResponseEntity<?> updateGame(@PathVariable Long id, @Valid @RequestBody UpdateGameDTO updateGameDTO,BindingResult bindingResult) {
            try {
                if (bindingResult.hasErrors()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErroMessage(bindingResult));//N'affiche pas le message voulu !!!
                }                                                      
                GameDTO updatedGame = gameService.updateGame(id, updateGameDTO);System.out.println("in try after update");
                return ResponseEntity.status(HttpStatus.OK).body(updatedGame);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            catch (Exception e) {System.out.println("in second catch");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating the player : "+e.getMessage());
            }
        }

        @DeleteMapping("delete/{id}")
        public ResponseEntity<?> deleteGame(@PathVariable Long id){
            try {
                gameService.deleteGame(id);
                return ResponseEntity.status(HttpStatus.OK).body("Game was deleted successfully");
            }
            catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Game with this id, "+e.getMessage());
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with the game deletion : "+e.getMessage());
            }
        }

        @GetMapping("get/{id}")
        public ResponseEntity<?> getMethodName(@PathVariable Long id) {
            try{
                GameDTO gameDTO = gameService.getGameInformations(id);
                return ResponseEntity.status(HttpStatus.OK).body(gameDTO);
            }
            catch(RuntimeException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting the game : "+e.getMessage());
            }
        }
        

        public String getErroMessage(BindingResult bindingResult){
                return bindingResult.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                        .orElse("Validation failed");
        }
    }
