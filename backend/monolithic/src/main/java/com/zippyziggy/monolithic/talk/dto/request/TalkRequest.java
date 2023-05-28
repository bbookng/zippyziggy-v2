package com.zippyziggy.monolithic.talk.dto.request;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class TalkRequest {

	@Nullable
	private String promptUuid;
	private String title;
	private List<MessageRequest> messages;

}
