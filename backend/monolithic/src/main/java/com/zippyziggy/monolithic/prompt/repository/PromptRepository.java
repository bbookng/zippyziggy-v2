package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PromptRepository extends JpaRepository<Prompt, Long> {

	@Modifying
	@Query("update Prompt set hit = hit + 1 where id = :promptId")
	int updateHit(@Param(value = "promptId") Long promptId);

	Optional<Prompt> findByPromptUuidAndStatusCode(UUID promptUuid, StatusCode statusCode);

	Optional<Prompt> findByOriginPromptUuidAndPromptUuid(UUID originPromptUuid, UUID promptUuid);

	Optional<Prompt> findByPromptUuid(UUID promptUuid);

	Page<Prompt> findAllByOriginPromptUuidAndStatusCode(UUID promptUuid, StatusCode statusCode, Pageable pageable);

	Page<Prompt> findAllByMemberUuidAndStatusCode(UUID memberUuid, StatusCode statusCode, Pageable pageable);

	Long countAllByOriginPromptUuidAndStatusCode(UUID promptUuid, StatusCode statusCode);

	Page<Prompt> findAllByStatusCodeAndPromptBookmarksMemberUuid(StatusCode statusCode, UUID memberUuid, Pageable pageable);
}
