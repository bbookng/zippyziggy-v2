package com.zippyziggy.monolithic.talk.dto.response;

import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import com.zippyziggy.monolithic.prompt.dto.response.PromptCardResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class TalkDetailResponse {

	private String title;

	@Nullable
	private MemberResponse originMember;

	@NotNull
	private WriterResponse writer;

	private Boolean isLiked;
	private Long likeCnt;

	private long regDt;
	private long updDt;

	private List<MessageResponse> messages;

	@NotNull
	private PromptCardResponse originPrompt;

	@Nullable
	private List<TalkListResponse> talkList;

	private String model;

}
