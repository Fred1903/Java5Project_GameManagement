package be.helb_prigogine.game_manager.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class PlayerWebClient implements IPlayerWebClient {
    private final WebClient webClient;

    public PlayerWebClient(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public boolean isPlayerExisting(Long idPlayer) {
        try {
            // Appeler l'endpoint REST pour récupérer le joueur
            webClient.get().uri("/get/{id}", idPlayer).retrieve().toEntity(String.class)
                     .block(); // Bloque jusqu'à ce que la réponse arrive
            return true; // Si aucune exception, le joueur existe
        } catch (WebClientResponseException.NotFound e) {
            // Gestion du 404
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to check host id", e);
        }
    }    
}
