package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;

@Data
public class SearchPrompt {

    public static SearchPrompt of(
            Prompt prompt,
            SearchPromptResponse fromPrompt,
            WriterResponse writer
    ) {
        String originalPromptUuid = null == prompt.getOriginPromptUuid() ? "" : prompt.getOriginPromptUuid().toString();
        long regDt = prompt.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        long updDt = prompt.getUpdDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        return SearchPrompt.builder()
                .promptUuid(prompt.getPromptUuid().toString())
                .title(prompt.getTitle())
                .description(prompt.getDescription())
                .category(prompt.getCategory().getDescription())
                .originalPromptUuid(originalPromptUuid)
                .regDt(regDt)
                .updDt(updDt)
                .likeCnt(prompt.getLikeCnt())
                .hit(prompt.getHit())

                .thumbnail(prompt.getThumbnail())
                .talkCnt(fromPrompt.getTalkCnt().intValue())
                .commentCnt(fromPrompt.getCommentCnt().intValue())
                .isLiked(fromPrompt.getIsLiked())
                .isBookmarked(fromPrompt.getIsBookmarked())

                .writer(writer)
                .build();
    }

    private final String promptUuid;
    private final String title;
    private final String description;
    private final String category;
    private final String originalPromptUuid;

    private final String thumbnail;
    private final Integer hit;
    private final Long regDt;
    private final Long updDt;

    private final Integer talkCnt;
    private final Integer commentCnt;
    private final Long likeCnt;
    private final Boolean isLiked;
    private final Boolean isBookmarked;

    private final WriterResponse writer;

    @Builder
    public SearchPrompt(
            String promptUuid,
            String title,
            String description,
            String category,
            String originalPromptUuid,

            String thumbnail,
            Integer hit,
            Long regDt,
            Long updDt,

            Integer talkCnt,
            Integer commentCnt,
            Long likeCnt,
            Boolean isLiked,
            Boolean isBookmarked,

            WriterResponse writer
    ) {
        this.promptUuid = promptUuid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.originalPromptUuid = originalPromptUuid;

        this.thumbnail = thumbnail;
        this.hit = hit;
        this.regDt = regDt;
        this.updDt = updDt;

        this.talkCnt = talkCnt;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;

        this.writer = writer;
    }

}
