package com.zippyziggy.monolithic.prompt.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AppChatGptRequest {
    List<ChatGptMessage> messages;
}
