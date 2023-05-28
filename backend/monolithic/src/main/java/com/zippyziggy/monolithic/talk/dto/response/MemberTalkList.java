package com.zippyziggy.monolithic.talk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberTalkList {
    private Long totalTalksCnt;
    private Integer totalPageCnt;
    private List<MemberTalk> memberTalkList;

}
