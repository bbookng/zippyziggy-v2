package com.zippyziggy.monolithic.prompt.dto.response;

import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Long prompId;
    private Integer hit;
    private Long likeCnt;
    private Integer score;
}
