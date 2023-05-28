package com.zippyziggy.monolithic.member.dto.request;


import com.zippyziggy.monolithic.member.model.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequestDto {

    private String nickname;
    private String profileImg;
    private String name;
    private Platform platform;
    private String platformId;

}
