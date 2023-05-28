package com.zippyziggy.monolithic.prompt.dto.request;

import lombok.Data;

@Data
public class GptApiRequest {

	private String prefix;
	private String example;
	private String suffix;
}
