package be.helb_prigogine.game_manager.services;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class PlayerWebClientService implements IPlayerWebClientService {
    private final WebClient webClient;

    public PlayerWebClientService(WebClient webClient) {
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


    @Override
    public void updatePlayerStatistics(Long idPlayer, Integer points) {
        try{
            Map<String, Integer> requestBody = Map.of("points", points);
            webClient.put().uri("/update/stats/{id}",idPlayer).bodyValue(requestBody ).retrieve().toBodilessEntity().block();
            System.out.println("update/stats/{id}"+idPlayer);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to update player statistics for ID: " + idPlayer, e);
        }
    }  

}
