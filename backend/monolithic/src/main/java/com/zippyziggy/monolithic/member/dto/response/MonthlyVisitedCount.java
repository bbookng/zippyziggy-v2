package com.zippyziggy.monolithic.member.dto.response;

import com.zippyziggy.monolithic.member.model.VisitedMemberCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyVisitedCount {
    private String dailyDate;
    private Long dailyVisitedCount;


    public static MonthlyVisitedCount from(VisitedMemberCount visitedMemberCount) {
        return MonthlyVisitedCount.builder()
                .dailyDate(visitedMemberCount.getNowDate())
                .dailyVisitedCount(visitedMemberCount.getVisitedCount()).build();
    }
}
