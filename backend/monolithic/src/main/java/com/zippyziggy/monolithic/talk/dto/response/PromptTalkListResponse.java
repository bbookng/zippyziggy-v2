package com.zippyziggy.monolithic.talk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PromptTalkListResponse {

	private int talkCnt;
	private List<TalkListResponse> talks;

}
