package mushikStudy.com.mushikStudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DevConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
