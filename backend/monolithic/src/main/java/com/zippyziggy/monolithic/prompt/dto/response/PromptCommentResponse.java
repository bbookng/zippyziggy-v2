package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.prompt.model.PromptComment;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.ZoneId;

@Data
@Getter
@Builder
public class PromptCommentResponse {

	private Long commentId;
	private MemberResponse member;
	private long regDt;
	private long updDt;
	private String content;

	public static PromptCommentResponse from(PromptComment comment) {

		long regDt = comment.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		long updDt = comment.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

		PromptCommentResponse response = PromptCommentResponse.builder()
			.commentId(comment.getId())
			.regDt(regDt)
			.updDt(updDt)
			.content(comment.getContent())
			.build();

		return response;
	}

}
