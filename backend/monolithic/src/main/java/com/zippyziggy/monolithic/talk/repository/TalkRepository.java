package com.zippyziggy.monolithic.talk.repository;

import com.zippyziggy.monolithic.talk.model.Talk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TalkRepository extends JpaRepository<Talk, Long> {
	Page<Talk> findAllByPromptPromptUuid(UUID promptUuid, Pageable pageable);

	long countAllByPromptPromptUuid(UUID promptUuid);

	List<Talk> findAllByMemberUuid(UUID memberUuid);

	Page<Talk> findTalksByMemberUuid(UUID memberUuid, Pageable pageable);

	@Modifying
	@Query("update Talk set hit = hit + 1 where id = :talkId")
	int updateHit(@Param(value = "talkId") Long talkId);

	@Query("select distinct t from Talk t left join t.messages m where t.title like %:keyword% or m.content like %:keyword%")
    Page<Talk> findByKeywordOnly(String keyword, Pageable pageable);

}
