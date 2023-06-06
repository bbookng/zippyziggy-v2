package com.zippyziggy.monolithic.talk.dto.response;

import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TalkListResponse {

	private Long talkId;
	private String title;
	private String question;
	private String answer;
	private WriterResponse writer;
	private Long likeCnt;
	private Long commentCnt;
	private Boolean isLiked;
	private String model;

}
