package com.zippyziggy.monolithic.prompt.model;

import com.zippyziggy.monolithic.prompt.dto.request.PromptCommentRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class PromptComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private UUID memberUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prompt_uuid", nullable = false)
	private Prompt prompt;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private LocalDateTime regDt;


	private LocalDateTime updDt;

	public void setContent(String content) {
		this.content = content;
	}

	public void setUpdDt(LocalDateTime updDt) {
		this.updDt = updDt;
	}


	public static PromptComment from(PromptCommentRequest data, UUID crntMemberUuid, Prompt prompt) {
		return PromptComment.builder()
				.memberUuid(crntMemberUuid)
				.prompt(prompt)
				.regDt(LocalDateTime.now())
				.content(data.getContent())
				.build();
	}

}
