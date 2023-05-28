package com.zippyziggy.monolithic.talk.dto.request;

import com.zippyziggy.monolithic.talk.model.Role;
import lombok.Data;

@Data
public class MessageRequest {

	private Role role;
	private String content;
}
