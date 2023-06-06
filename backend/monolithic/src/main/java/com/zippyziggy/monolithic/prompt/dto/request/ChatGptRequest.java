package com.zippyziggy.monolithic.prompt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptRequest {
    private String model;
    private List<ChatGptMessage> messages;

    public ChatGptRequest(String model, List<ChatGptMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

    public ChatGptRequest(String model, AppChatGptRequest data) {
        this.model = model;
        this.messages = data.messages;
    }
}
