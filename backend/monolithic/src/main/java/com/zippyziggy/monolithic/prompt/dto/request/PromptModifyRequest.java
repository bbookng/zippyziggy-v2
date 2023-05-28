package com.zippyziggy.monolithic.prompt.dto.request;

import com.zippyziggy.monolithic.prompt.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptModifyRequest {

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private Category category;

}
