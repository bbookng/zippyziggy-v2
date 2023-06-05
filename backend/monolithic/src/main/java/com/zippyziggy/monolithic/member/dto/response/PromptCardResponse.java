package com.zippyziggy.monolithic.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@Builder
public class PromptCardResponse {

	private String promptUuid;
	private String thumbnail;
	private String title;
	private String description;

	private WriterResponse writer;
	private Long likeCnt;
	private Long commentCnt;
	private Long forkCnt;
	private Long talkCnt;
	private long hit;

	private long regDt;
	private long updDt;

	private Boolean isBookmarked;
	private Boolean isLiked;

}
