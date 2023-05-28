package com.zippyziggy.monolithic.talk.repository;

import com.zippyziggy.monolithic.talk.model.TalkComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TalkCommentRepository extends JpaRepository<TalkComment, Long> {
    Long countAllByTalk_Id(Long id);

    Page<TalkComment> findAllByTalk_Id(Long talkId, Pageable pageable);

	List<TalkComment> findAllByMemberUuid(UUID memberUuid);
}
