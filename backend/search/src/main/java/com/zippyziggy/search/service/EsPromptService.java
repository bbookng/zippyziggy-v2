package com.zippyziggy.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.zippyziggy.search.client.MemberClient;
import com.zippyziggy.search.client.PromptClient;
import com.zippyziggy.search.dto.response.server.SyncEsPrompt;
import com.zippyziggy.search.dto.response.ExtensionSearchPromptList;
import com.zippyziggy.search.dto.response.server.PromptDetailResponse;
import com.zippyziggy.search.dto.response.SearchPrompt;
import com.zippyziggy.search.dto.response.SearchPromptList;
import com.zippyziggy.search.dto.response.WriterResponse;
import com.zippyziggy.search.dto.response.server.CntResponse;
import com.zippyziggy.search.dto.response.server.ExtensionSearchPrompt;
import com.zippyziggy.search.exception.EsPromptNotFoundException;
import com.zippyziggy.search.exception.IllegalUpdateRequestException;
import com.zippyziggy.search.exception.PromptNotFoundException;
import com.zippyziggy.search.model.EsPrompt;
import com.zippyziggy.search.repository.EsPromptRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EsPromptService {

    private final EsPromptRepository esPromptRepository;

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final PromptClient promptClient;
    private final MemberClient memberClient;
    private final ElasticsearchClient elasticsearchClient;

    public SearchPromptList searchPrompts(
        String crntMemberUuid,
        String keyword,
        String category,
        Integer page,
        Integer size,
        String sort
    ) {
        final Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        final Pageable pageable = PageRequest.of(page, size, sortBy);

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        final Page<EsPrompt> pagedEsPrompt = search(keyword, category, pageable);
        final long totalPromptsCnt = pagedEsPrompt.getTotalElements();
        final int totalPageCnt = pagedEsPrompt.getTotalPages();

        final List<SearchPrompt> searchPrompts = new ArrayList<>();
        for (EsPrompt esPrompt : pagedEsPrompt) {
            // promptDetailResponse 조회
            final UUID promptUuid = UUID.fromString(esPrompt.getPromptUuid());
            final PromptDetailResponse promptDetailResponse = circuitBreaker
                .run(() -> promptClient
                    .getPromptDetail(promptUuid, crntMemberUuid)
                    .orElseThrow(PromptNotFoundException::new));

            // 사용자 조회
            //TODO server to server api 만든 후 Member application에서 호출하는 방식으로 변경해야함
            final WriterResponse writerResponse = promptDetailResponse.getWriter();

            final CntResponse cntResponse = circuitBreaker
                .run(() -> promptClient
                    .getCntOfPrompt(promptUuid));

            // 로그인 여부에 따른 좋아요/북마크 여부
            final Boolean isLiked =
                !crntMemberUuid.equals("defaultValue") && promptDetailResponse.getIsLiked();
            final Boolean isBookmarked =
                !(crntMemberUuid.equals("defaultValue")) && promptDetailResponse.getIsBookmarked();

            // dto로 변환하기
            searchPrompts.add(SearchPrompt.of(
                esPrompt,
                promptDetailResponse,
                cntResponse.getTalkCnt(),
                cntResponse.getCommentCnt(),
                promptDetailResponse.getLikeCnt(),
                isLiked,
                isBookmarked,
                writerResponse));

        }
        return SearchPromptList.of(totalPromptsCnt, totalPageCnt, searchPrompts);
    }

    public ExtensionSearchPromptList extensionSearch(
        String crntMemberUuid,
        String keyword,
        String category,
        Integer page,
        Integer size,
        String sort
    ) {
        final Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        final Pageable pageable = PageRequest.of(page, size, sortBy);

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        final Page<EsPrompt> pagedEsPrompt = search(keyword, category, pageable);
        final long totalPromptsCnt = pagedEsPrompt.getTotalElements();
        final int totalPageCnt = pagedEsPrompt.getTotalPages();

        final List<ExtensionSearchPrompt> searchPrompts = new ArrayList<>();
        for (EsPrompt esPrompt : pagedEsPrompt) {
            // promptDetailResponse 조회
            final UUID promptUuid = UUID.fromString(esPrompt.getPromptUuid());
            final PromptDetailResponse promptDetailResponse = circuitBreaker
                .run(() -> promptClient
                    .getPromptDetail(promptUuid, crntMemberUuid)
                    .orElseThrow(PromptNotFoundException::new));

            // 사용자 조회
            //TODO server to server api 만든 후 Member application에서 호출하는 방식으로 변경해야함
            WriterResponse writerResponse = promptDetailResponse.getWriter();

            final CntResponse cntResponse = circuitBreaker
                .run(() -> promptClient
                    .getCntOfPrompt(promptUuid));

            // 로그인 여부에 따른 좋아요/북마크 여부
            final Boolean isLiked =
                !crntMemberUuid.equals("defaultValue") && promptDetailResponse.getIsLiked();
            final Boolean isBookmarked =
                !(crntMemberUuid.equals("defaultValue")) && promptDetailResponse.getIsBookmarked();

            // dto로 변환하기
            searchPrompts.add(ExtensionSearchPrompt.of(
                esPrompt,
                promptDetailResponse,
                cntResponse.getTalkCnt(),
                cntResponse.getCommentCnt(),
                promptDetailResponse.getLikeCnt(),
                isLiked,
                isBookmarked,
                writerResponse));

        }
        return ExtensionSearchPromptList.of(totalPromptsCnt, totalPageCnt, searchPrompts);
    }

    public void insertDocument(SyncEsPrompt syncEsPrompt) {
        log.info(String.valueOf(syncEsPrompt));
        final EsPrompt esPrompt = EsPrompt.of(syncEsPrompt);
        esPromptRepository.save(esPrompt);
    }

    public void updateDocument(String promptUuid, SyncEsPrompt syncEsPrompt) {
        final EsPrompt oldEsPrompt = esPromptRepository
            .findEsPromptByPromptUuid(promptUuid)
            .orElseThrow(EsPromptNotFoundException::new);
        esPromptRepository.delete(oldEsPrompt);
        final EsPrompt newEsPrompt = EsPrompt.of(syncEsPrompt);
        esPromptRepository.save(newEsPrompt);
    }

    public void deleteDocument(String promptUuid) {
        final EsPrompt esPrompt = esPromptRepository
            .findEsPromptByPromptUuid(promptUuid)
            .orElseThrow(EsPromptNotFoundException::new);
        esPromptRepository.delete(esPrompt);
    }

    public void updateHit(String promptUuid, Integer hit) {
        final EsPrompt esPrompt = esPromptRepository
                .findEsPromptByPromptUuid(promptUuid)
                .orElseThrow(EsPromptNotFoundException::new);
        esPrompt.setHit(hit);
        try {
        elasticsearchClient.update(new UpdateRequest.Builder<Void, EsPrompt>()
                .index("prompt")
                .id(esPrompt.getId())
                .doc(esPrompt)
                .build(),
                Void.class);
        } catch (IOException e) {
            throw new IllegalUpdateRequestException();
        }
    }

    public void updateLikeCnt(String promptUuid, Integer likeCnt) {
        final EsPrompt esPrompt = esPromptRepository
                .findEsPromptByPromptUuid(promptUuid)
                .orElseThrow(EsPromptNotFoundException::new);
        esPrompt.setLikeCnt(likeCnt);
        try {
            final UpdateRequest updateRequest = new UpdateRequest.Builder<EsPrompt, EsPrompt>()
                    .index("prompt")
                    .id(esPrompt.getId())
                    .doc(esPrompt)
                    .build();
            log.info(updateRequest.toString());
            final UpdateResponse result = elasticsearchClient.update(updateRequest, EsPrompt.class);
            log.info(result.toString());
        } catch (IOException e) {
            throw new IllegalUpdateRequestException();
        }
    }

    private Page<EsPrompt> search(
        String keyword,
        String category,
        Pageable pageable
    ) {
        Page<EsPrompt> pagedEsPrompt = null;
        keyword = (keyword.equals("")) ? null : keyword;
        category = (category.equals("ALL")) ? null : category;

        if (null != keyword & null != category) {
            pagedEsPrompt = esPromptRepository
                .findByKeywordAndCategory(keyword, category, pageable);

        } else if (null == keyword & null != category) {
            pagedEsPrompt = esPromptRepository
                .findByCategory(category, pageable);

        } else if (null != keyword & null == category) {
            pagedEsPrompt = esPromptRepository
                .findByKeywordOnly(keyword, pageable);

        } else if (null == keyword & null == category) {
            pagedEsPrompt = esPromptRepository
                .findAll(pageable);
        }

        return pagedEsPrompt;
    }
}
