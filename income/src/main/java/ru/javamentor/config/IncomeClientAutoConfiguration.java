package ru.javamentor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.javamentor.client.IncomeClient;

@Configuration
public class IncomeClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public IncomeClient incomeClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new IncomeClient(restTemplate, objectMapper);
    }
}