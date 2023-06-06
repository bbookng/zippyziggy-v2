package com.zippyziggy.monolithic.prompt.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecentPromptCardListResponse {

    private final List<PromptCardResponse> promptCardResponseList;


}
