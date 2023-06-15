package com.zippyziggy.monolithic.prompt.dto.response;

import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;

@Data
public class ExtensionSearchPrompt {

    public static ExtensionSearchPrompt of(
            Prompt prompt,
            SearchPromptResponse fromPrompt,
            WriterResponse writer
    ) {

        String originalPromptUuid = null == prompt.getOriginPromptUuid() ? "" : prompt.getOriginPromptUuid().toString();
        long regDt = prompt.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        long updDt = prompt.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        return ExtensionSearchPrompt.builder()
                .promptUuid(prompt.getPromptUuid().toString())
                .title(prompt.getTitle())
                .description(prompt.getDescription())
                .category(prompt.getCategory().getDescription())
                .prefix(prompt.getPrefix())
                .suffix(prompt.getSuffix())
                .example(prompt.getExample())
                .originalPromptUuid(originalPromptUuid)
                .regDt(regDt)
                .updDt(updDt)
                .likeCnt(prompt.getLikeCnt().longValue())
                .hit(prompt.getHit())

                .thumbnail(fromPrompt.getThumbnail())
                .talkCnt(fromPrompt.getTalkCnt().intValue())
                .commentCnt(fromPrompt.getCommentCnt().intValue())
                .isLiked(fromPrompt.getIsLiked())
                .isBookmarked(fromPrompt.getIsBookmarked())

                .writerResponse(writer)
                .build();
    }

    private final String promptUuid;
    private final String title;
    private final String description;
    private final String category;
    private final String prefix;
    private final String suffix;
    private final String example;
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

    private final WriterResponse writerResponse;

    @Builder
    public ExtensionSearchPrompt(
            String promptUuid,
            String title,
            String description,
            String category,
            String prefix,
            String suffix,
            String example,
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

            WriterResponse writerResponse
    ) {
        this.promptUuid = promptUuid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.prefix = prefix;
        this.suffix = suffix;
        this.example = example;
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

        this.writerResponse = writerResponse;
    }

}
