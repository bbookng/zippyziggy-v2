package com.zippyziggy.monolithic.prompt.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class PromptTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private UUID memberUuid;

	@Column(length = 255)
	private String title;

	@Column(length = 255)
	private String description;

	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer hit;

	@Column(nullable = false)
	private LocalDateTime regDt;

	@Column(nullable = false)
	private LocalDateTime updDt;

	@Column(nullable = false, length = 10)
	private Category category;

	@Lob
	private String prefix;

	@Lob
	private String suffix;

	@Lob
	private String example;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private String promptUuid;

	@Column(nullable = false, columnDefinition = "BINARY(16)")
	private Long originPromptUuId;

	public void setOriginPromptId(Long originPromptId) {
		this.originPromptUuId = originPromptUuId;
	}

}
