package com.zippyziggy.monolithic.talk.repository;

import com.zippyziggy.monolithic.talk.model.TalkLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TalkLikeRepository extends JpaRepository<TalkLike, Long> {
	Optional<TalkLike> findByTalk_IdAndMemberUuid(Long talkId, UUID crntMemberUuid);

	Boolean existsByTalk_IdAndMemberUuid(Long talkId, UUID crntMemberUuid);

	Long countAllByTalkId(Long talkId);

	List<TalkLike> findAllByMemberUuid(UUID memberUuid);
}
