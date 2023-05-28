package com.zippyziggy.monolithic.prompt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ForkedPromptListResponse {

	private Long forkCnt;
	private List<PromptCardResponse> forkedPromptResponseList;

}
