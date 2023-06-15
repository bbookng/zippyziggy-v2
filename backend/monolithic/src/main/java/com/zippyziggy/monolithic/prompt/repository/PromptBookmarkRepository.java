package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.PromptBookmark;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromptBookmarkRepository extends JpaRepository<PromptBookmark, Long> {
    Long countAllByMemberUuidAndPrompt(UUID crntMemberUuid, Prompt prompt);

	Optional<PromptBookmark> findByMemberUuidAndPrompt(UUID crntMemberUuid, Prompt originPrompt);

	List<PromptBookmark> findAllByMemberUuid(UUID memberUuid);

	@Query("SELECT pb.prompt FROM PromptBookmark pb WHERE pb.memberUuid = :memberUuid AND pb.prompt.statusCode = :statusCode")
	Page<Prompt> findAllPromptsByMemberUuidAndStatusCode(@Param("memberUuid") UUID memberUuid, @Param("statusCode") StatusCode statusCode, Pageable pageable);


	boolean existsByMemberUuidAndPrompt_PromptUuid(
			UUID memberUuid, UUID promptUuid);


}
