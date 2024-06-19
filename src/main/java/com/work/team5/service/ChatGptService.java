package com.work.team5.service;

import com.work.team5.dto.ChatGptRequest;
import com.work.team5.dto.ChatGptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    private static final Logger logger = LoggerFactory.getLogger(ChatGptService.class);

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.model}")
    private String model;

    private final RestTemplate restTemplate;

    public ChatGptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChatGptResponse getChatGptResponse(String prompt) {
        try {
            ChatGptRequest request = new ChatGptRequest(model, prompt);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            HttpEntity<ChatGptRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<ChatGptResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ChatGptResponse.class);
            logger.info("Received response from OpenAI API: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred while calling OpenAI API", e);
            return null;
        }
    }
}
