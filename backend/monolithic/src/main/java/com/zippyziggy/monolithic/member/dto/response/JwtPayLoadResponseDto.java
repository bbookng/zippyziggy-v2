package com.zippyziggy.monolithic.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayLoadResponseDto {

    private String sub;
    private UUID userUuid;
    private String nickname;
    private Long exp;

}
