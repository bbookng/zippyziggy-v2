package com.zippyziggy.monolithic.talk.dto.response;

import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import com.zippyziggy.monolithic.talk.model.Talk;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;

@Data
public class SearchTalk {

    public static SearchTalk of(
            WriterResponse writer,
            Talk talk,
            Long commentCnt
    ) {
        long regDt = talk.getRegDt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        return SearchTalk.builder()
                .writer(writer)
                .talkId(talk.getId())
                .title(talk.getTitle())
                .regDt(regDt)
                .commentCnt(commentCnt)
                .likeCnt(talk.getLikeCnt())
                .question(talk.getMessages().get(0).getContent())
                .answer(talk.getMessages().get(1).getContent())
                .hit(talk.getHit().intValue())
                .model(talk.getModel())
                .build();
    }

    private final WriterResponse writer;
    private final Long talkId;
    private final String title;
    private final Long regDt;
    private final Long likeCnt;
    private final Long commentCnt;
    private final Integer hit;
    private final String question;
    private final String answer;
    private final String model;


    @Builder
    public SearchTalk(
            WriterResponse writer,
            Long talkId,
            String title,
            Integer hit,
            Long regDt,
            Long commentCnt,
            Long likeCnt,
            String question,
            String answer,
            String model) {
        this.writer = writer;
        this.talkId = talkId;
        this.title = title;
        this.hit = hit;
        this.regDt = regDt;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.question = question;
        this.answer = answer;
        this.model = model;
    }

}
