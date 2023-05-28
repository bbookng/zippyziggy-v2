package com.zippyziggy.monolithic.talk.repository;

import com.zippyziggy.monolithic.talk.model.Message;
import com.zippyziggy.monolithic.talk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findFirstByTalkIdAndRole(Long id, Role role);
}
