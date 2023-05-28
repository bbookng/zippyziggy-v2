package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@Getter @Setter
@AllArgsConstructor
@Builder
public class PromptDetailResponse {

	private WriterResponse writer;

	@Nullable
	private OriginerResponse originer;

	private String title;
	private String description;
	private String thumbnail;
	private Long likeCnt;
	private Boolean isLiked;
	private Boolean isBookmarked;
	private Boolean isForked;
	private String category;
	private long regDt;
	private long updDt;
	private long hit;
	private UUID originPromptUuid;
	private String originPromptTitle;

	private PromptMessageResponse messageResponse;

}
