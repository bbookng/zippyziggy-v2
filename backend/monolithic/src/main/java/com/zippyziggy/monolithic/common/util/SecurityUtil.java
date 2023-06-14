package com.zippyziggy.monolithic.common.util;

import com.zippyziggy.monolithic.member.dto.response.MemberInformResponseDto;
import com.zippyziggy.monolithic.member.filter.User.CustomUserDetail;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtil {

    private SecurityUtil() {
    }

    @Autowired
    private MemberRepository memberRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    // SecurityContext에 저장된 유저 정보 가져오기
    public Member getCurrentMember() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return null;
        }

        String userUuid = null;
        if (authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail springSecurityUser = (CustomUserDetail) authentication.getPrincipal();
            userUuid = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            userUuid = (String) authentication.getPrincipal();
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            logger.debug("유효하지 않은 UUID 문자열입니다.");
            return null;
        }


        return memberRepository.findByUserUuid(uuid);
    }

    public UUID getCurrentMemberUUID() {

        CustomUserDetail principal = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userUuid = principal.getUsername();
        UUID uuid = UUID.fromString(userUuid);

        return null;
    }

    // SecurityContext에 저장된 유저 정보 가져오기
    public MemberInformResponseDto getCurrentMemberInformResponseDto() {

        // 인증된 유저 가져오기
        CustomUserDetail principal = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userUuid = principal.getUsername();
        String MemberKey = "member" + userUuid;


        // Redis 캐쉬에 존재하지 않는 경우
        UUID uuid = UUID.fromString(userUuid);
        Member member = memberRepository.findByUserUuid(uuid);

        return MemberInformResponseDto.builder()
                .nickname(member.getNickname())
                .profileImg(member.getProfileImg())
                .userUuid(member.getUserUuid()).build();

    }

}
