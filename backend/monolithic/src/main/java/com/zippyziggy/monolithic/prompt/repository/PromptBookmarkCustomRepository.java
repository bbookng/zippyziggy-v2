package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PromptBookmarkCustomRepository {

    public Page<Prompt> findAllPromptsByMemberUuid(UUID memberUuid, Pageable pageable);
}
