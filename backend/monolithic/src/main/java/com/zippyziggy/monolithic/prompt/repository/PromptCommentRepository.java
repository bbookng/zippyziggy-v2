package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.PromptComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PromptCommentRepository extends JpaRepository<PromptComment, Long> {
	Page<PromptComment> findAllByPromptPromptUuid(UUID promptUuid, Pageable pageable);

	List<PromptComment> findAllByPromptPromptUuid(UUID promptUuid);

	Long countAllByPromptPromptUuid(UUID promptUuid);

	List<PromptComment> findAllByMemberUuid(UUID memberUuid);
}
