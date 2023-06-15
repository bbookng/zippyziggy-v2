package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.PromptLike;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PromptLikeRepository extends JpaRepository<PromptLike, Long> {
    Long countAllByMemberUuidAndPrompt(UUID memberUuid, Prompt prompt);

    // 해당 멤버가 좋아요를 누른 promptId 받아오기
    List<PromptLike> findAllByMemberUuidOrderByRegDtDesc(UUID memberUuid, Pageable pageable);

    // 좋아요 상태 조회하기
    Optional<PromptLike> findByPromptAndMemberUuid(Prompt prompt, UUID memberUuid);

    List<PromptLike> findAllByMemberUuid(UUID memberUuid);

    @Query("SELECT pl.prompt FROM PromptLike pl WHERE pl.memberUuid = :memberUuid AND pl.prompt.statusCode = :statusCode")
    Page<Prompt> findAllPromptsByMemberUuidAndStatusCode(@Param("memberUuid") UUID memberUuid, @Param("statusCode") StatusCode statusCode, Pageable pageable);

    boolean existsByMemberUuidAndPrompt_PromptUuid(
        UUID memberUuid, UUID promptUuid);


}
