package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
@Builder
public class PromptResponse {

	private UUID promptUuid;
	private String title;
	private String description;
	private String thumbnail;
	private String category;
	private PromptMessageResponse message;

	public static PromptResponse from(Prompt prompt) {
		PromptMessageResponse message = new PromptMessageResponse(prompt.getPrefix(), prompt.getExample(),
			prompt.getSuffix());

		PromptResponse response = PromptResponse.builder()
			.promptUuid(prompt.getPromptUuid())
			.title(prompt.getTitle())
			.description(prompt.getDescription())
			.thumbnail(prompt.getThumbnail())
			.category(prompt.getCategory().toString())
			.message(message)
			.build();

		return response;
	}

}
