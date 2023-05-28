package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.ZoneId;

@Data
@Getter
@Builder
public class SearchPromptResponse {
    private String thumbnail;
    private Long updDt;
    private Long talkCnt;
    private Long commentCnt;
    private Boolean isLiked;
    private Boolean isBookmarked;

    public static SearchPromptResponse from(
        Prompt prompt,
        Long talkCnt,
        Long commentCnt,
        Boolean isLiked,
        Boolean isBookmarked
    ) {
        return SearchPromptResponse.builder()
                .thumbnail(prompt.getThumbnail())
                .updDt(prompt.getUpdDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond())
                .talkCnt(talkCnt)
                .commentCnt(commentCnt)
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .build();
    }
}
