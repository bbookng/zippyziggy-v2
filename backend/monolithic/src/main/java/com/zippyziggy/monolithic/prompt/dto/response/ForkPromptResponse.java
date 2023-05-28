package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
@Builder
public class ForkPromptResponse {

	private String title;
	private String description;
	private String thumbnail;
	private String category;
	private PromptMessageResponse message;
	private UUID originalUuid;
	private UUID promptUuid;

	public static ForkPromptResponse from(Prompt prompt) {
		PromptMessageResponse message = new PromptMessageResponse(prompt.getPrefix(), prompt.getExample(),
			prompt.getSuffix());

		ForkPromptResponse response = ForkPromptResponse.builder()
			.title(prompt.getTitle())
			.description(prompt.getDescription())
			.thumbnail(prompt.getThumbnail())
			.category(prompt.getCategory().toString())
			.message(message)
			.originalUuid(prompt.getOriginPromptUuid())
			.promptUuid(prompt.getPromptUuid())
			.build();

		return response;
	}
}
