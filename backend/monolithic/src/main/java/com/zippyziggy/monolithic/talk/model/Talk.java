package com.zippyziggy.monolithic.talk.model;

import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.talk.dto.request.EsTalkRequest;
import com.zippyziggy.monolithic.talk.dto.request.TalkRequest;
import com.zippyziggy.monolithic.talk.dto.response.MessageResponse;
import com.zippyziggy.monolithic.talk.dto.response.TalkDetailResponse;
import com.zippyziggy.monolithic.talk.dto.response.TalkListResponse;
import com.zippyziggy.monolithic.talk.dto.response.TalkResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Talk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prompt_uuid", nullable = true)
	private Prompt prompt;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private UUID memberUuid;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = false)
	private LocalDateTime regDt;

	private Long likeCnt;

	private Long hit;

	@OneToMany(mappedBy = "talk", cascade = CascadeType.ALL)
	private List<Message> messages;

	private String model;

	public void setPrompt(Prompt prompt) {
		this.prompt = prompt;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setLikeCnt(Long likeCnt) {
		this.likeCnt = likeCnt;
	}

	public static Talk from(TalkRequest data, UUID crntMemberUuid) {
		return Talk.builder()
				.memberUuid(crntMemberUuid)
				.title(data.getTitle())
				.regDt(LocalDateTime.now())
				.likeCnt(0L)
				.model(data.getModel())
				.hit(0L)
				.build();
	}

	public TalkResponse toTalkResponse() {
		List<MessageResponse> messageResponses = this.messages.stream()
				.map(m -> m.toMessageResponse()).collect(Collectors.toList());
		return TalkResponse.builder()
				.talkId(this.id)
				.title(this.title)
				.regDt(this.regDt)
				.memberUuid(this.memberUuid)
				.messages(messageResponses)
				.build();
	}

	public TalkDetailResponse toDetailResponse(boolean isLiked, Long likeCnt, MemberResponse memberResponse) {

		long regDt = this.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		long updDt = this.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

		List<MessageResponse> messageResponses = this.getMessages()
				.stream()
				.map(message -> message.toMessageResponse())
				.collect(Collectors.toList());

		return TalkDetailResponse.builder()
				.model(this.model)
				.title(this.title)
				.isLiked(isLiked)
				.likeCnt(likeCnt)
				.regDt(regDt)
				.updDt(updDt)
				.messages(messageResponses)
				.writer(memberResponse.toWriterResponse())
				.build();
	}

	public EsTalkRequest toEsTalkRequest() {

		List<MessageResponse> messageResponses = this.getMessages()
				.stream()
				.map(message -> message.toMessageResponse())
				.collect(Collectors.toList());

		return EsTalkRequest.builder()
				.talkId(this.id)
				.promptUuid(this.getPrompt().getPromptUuid().toString())
				.memberUuid(this.getMemberUuid().toString())
				.title(this.getTitle())
				.regDt(this.regDt.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond())
				.likeCnt(this.likeCnt)
				.hit(this.hit)
				.esMessages(messageResponses)
				.build();
	}

	public TalkListResponse from(
			String question,
			String answer,
			MemberResponse member,
			Long likeCnt,
			Long commentCnt,
			Boolean isLiked
	) {
		return TalkListResponse.builder()
				.talkId(this.id)
				.title(this.title)
				.model(this.model)
				.question(question)
				.answer(answer)
				.writer(member.toWriterResponse())
				.likeCnt(likeCnt)
				.commentCnt(commentCnt)
				.isLiked(isLiked)
				.build();
	}
}
