package ru.javamentor.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class IncomeClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public IncomeClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> List<T> getUsersIncome(String url, Class<T> clazz) {
        String json = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обработке JSON", e);
        }
    }
}

