package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.prompt.dto.request.ChatGptMessage;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
public class ChatGptResponse {

    public List<Choice> choices;

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Choice {
        private int index;
        private ChatGptMessage message;
    }

}
