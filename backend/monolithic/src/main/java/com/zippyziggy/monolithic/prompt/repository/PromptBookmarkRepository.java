package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.PromptBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromptBookmarkRepository extends JpaRepository<PromptBookmark, Long>, PromptBookmarkCustomRepository {
    Long countAllByMemberUuidAndPrompt(UUID crntMemberUuid, Prompt prompt);

	Optional<PromptBookmark> findByMemberUuidAndPrompt(UUID crntMemberUuid, Prompt originPrompt);

	List<PromptBookmark> findAllByMemberUuid(UUID memberUuid);

	boolean existsByMemberUuidAndPrompt_PromptUuid(
		UUID memberUuid, UUID promptUuid);


}
