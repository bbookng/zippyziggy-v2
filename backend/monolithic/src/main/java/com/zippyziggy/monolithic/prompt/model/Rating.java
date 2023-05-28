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
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private UUID memberUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prompt_uuid", nullable = false)
	private Prompt prompt;

	@Column(nullable = false)
	private Integer score;

	@Column(nullable = false)
	private LocalDateTime regDt;

	public static Rating from(UUID memberUuid, Prompt prompt, Integer score) {
		LocalDateTime regDt = LocalDateTime.now();
		return Rating.builder()
				.memberUuid(memberUuid)
				.prompt(prompt)
				.score(score)
				.regDt(regDt).build();
	}


}
