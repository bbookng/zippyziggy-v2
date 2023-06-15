package com.zippyziggy.monolithic.prompt.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ExtensionSearchPromptList {

    public static ExtensionSearchPromptList of(
        Long totalPromptsCnt,
        Integer totalPageCnt,
        List<ExtensionSearchPrompt> searchPrompts
    ) {
        return new ExtensionSearchPromptList(totalPromptsCnt, totalPageCnt, searchPrompts);
    }

    private final Long totalPromptsCnt;
    private final Integer totalPageCnt;
    private final List<ExtensionSearchPrompt> extensionSearchPromptList;

}
