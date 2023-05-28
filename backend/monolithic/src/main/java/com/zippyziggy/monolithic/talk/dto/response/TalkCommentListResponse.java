package com.zippyziggy.monolithic.talk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class TalkCommentListResponse {

	private Long commentCnt;
	private List<TalkCommentResponse> comments;

}
