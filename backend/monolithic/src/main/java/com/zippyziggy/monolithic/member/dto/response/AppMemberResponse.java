package com.zippyziggy.monolithic.member.dto.response;

import com.zippyziggy.monolithic.member.model.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppMemberResponse {

    private MemberInformResponseDto memberInformResponseDto;
    private JwtToken jwtToken;
}
