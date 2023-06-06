package com.zippyziggy.monolithic.member.dto.response;

import com.zippyziggy.monolithic.member.exception.MemberNotFoundException;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.prompt.dto.response.OriginerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    public MemberResponse(String nickname, String profileImg) {
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    private String nickname;
    private String profileImg;
    private UUID userUuid;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .nickname(member.getNickname())
                .profileImg(member.getProfileImg())
                .userUuid(member.getUserUuid()).build();
    }

    public OriginerResponse toOriginerResponse() throws MemberNotFoundException {
        return new OriginerResponse(this.userUuid, this.profileImg, this.nickname);
    }

    public WriterResponse toWriterResponse() throws MemberNotFoundException {
        return new WriterResponse(this.userUuid, this.profileImg, this.nickname);
    }
}
