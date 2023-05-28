package com.zippyziggy.monolithic.prompt.repository;

import com.zippyziggy.monolithic.prompt.model.PromptReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PromptReportRepository extends JpaRepository<PromptReport, Long> {
    Long countAllByMemberUuidAndPrompt_PromptUuid(UUID memberUuid, UUID promptUuid);

    Page<PromptReport> findAllByOrderByRegDtDesc(Pageable pageable);
}
