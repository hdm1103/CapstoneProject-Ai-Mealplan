package com.work.team5.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGptRequest {
    private static final Logger logger = LoggerFactory.getLogger(ChatGptRequest.class);
    private String model;
    private List<Message> messages;

    public ChatGptRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt == null ? "" : prompt));
        logger.info("ChatGptRequest with prompt: {}", prompt);
    }
}
