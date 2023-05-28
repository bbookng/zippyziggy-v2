package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating findByMemberUuidAndPromptPromptUuid(UUID memberUuid, UUID promptUuid);

}
