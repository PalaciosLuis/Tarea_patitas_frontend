package pe.edu.cibertec.patitas_frontend_a.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppwebConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
