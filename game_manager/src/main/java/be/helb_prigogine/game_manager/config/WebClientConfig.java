package be.helb_prigogine.game_manager.config;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This file has to be named WebClientConfig, else it will not recognize 'WebClient.Builder' and the import

@Configuration
public class WebClientConfig {
    String urlPlayerManagement = "http://localhost:8081/players";
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(urlPlayerManagement).build();
    }
}
