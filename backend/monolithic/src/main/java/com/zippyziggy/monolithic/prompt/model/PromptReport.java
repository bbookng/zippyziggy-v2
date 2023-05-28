package com.zippyziggy.monolithic.prompt.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class PromptReport {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private UUID memberUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prompt_uuid", nullable = false)
	private Prompt prompt;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private LocalDateTime regDt;

	public static PromptReport from(UUID memberUuid, Prompt prompt, String content) {
		LocalDateTime regDt = LocalDateTime.now();
		return PromptReport.builder()
				.memberUuid(memberUuid)
				.prompt(prompt)
				.content(content)
				.regDt(regDt).build();
	}
}
