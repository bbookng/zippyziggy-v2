package com.zippyziggy.monolithic.prompt.dto.request;

import lombok.Data;

@Data
public class TalkCntRequest {
    private final Long talkId;
    private final Long cnt;
}
