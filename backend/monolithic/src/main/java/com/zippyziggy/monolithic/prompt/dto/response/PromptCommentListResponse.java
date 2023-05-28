package com.zippyziggy.monolithic.prompt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class PromptCommentListResponse {

	private Long commentCnt;
	private List<PromptCommentResponse> comments;

}
