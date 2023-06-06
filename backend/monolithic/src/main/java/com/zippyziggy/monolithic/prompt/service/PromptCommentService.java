package com.zippyziggy.monolithic.prompt.service;

import com.zippyziggy.monolithic.common.util.SecurityUtil;
import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import com.zippyziggy.monolithic.prompt.dto.request.PromptCommentRequest;
import com.zippyziggy.monolithic.prompt.dto.response.PromptCommentListResponse;
import com.zippyziggy.monolithic.prompt.dto.response.PromptCommentResponse;
import com.zippyziggy.monolithic.prompt.exception.ForbiddenMemberException;
import com.zippyziggy.monolithic.prompt.exception.PromptCommentNotFoundException;
import com.zippyziggy.monolithic.prompt.exception.PromptNotFoundException;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.PromptComment;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import com.zippyziggy.monolithic.prompt.repository.PromptCommentRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PromptCommentService {

	private final PromptCommentRepository promptCommentRepository;
	private final PromptRepository promptRepository;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;


	public PromptCommentListResponse getPromptCommentList(UUID promptUuid, Pageable pageable) {
		Page<PromptComment> commentList = promptCommentRepository.findAllByPromptPromptUuid(promptUuid, pageable);

		List<PromptCommentResponse> promptCommentResponseList = commentList.stream().map(comment -> {
			MemberResponse writerInfo = getMemberInfo(comment.getMemberUuid());
			PromptCommentResponse promptcommentList = PromptCommentResponse.from(comment);
			promptcommentList.setMember(writerInfo);

			return promptcommentList;
		}).collect(Collectors.toList());

		Long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(promptUuid);

		return new PromptCommentListResponse(commentCnt, promptCommentResponseList);
	}

	public PromptCommentResponse createPromptComment(UUID promptUuid, PromptCommentRequest data) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Prompt prompt = promptRepository.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN).orElseThrow(PromptNotFoundException::new);

		PromptComment promptComment = PromptComment.from(data, crntMemberUuid, prompt);
		promptCommentRepository.save(promptComment);

		return PromptCommentResponse.from(promptComment);
	}

	/*
	본인 댓글인지 확인하고 수정
	 */
	public PromptCommentResponse modifyPromptComment(Long commentId, PromptCommentRequest data) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		PromptComment comment = promptCommentRepository.findById(commentId)
			.orElseThrow(PromptCommentNotFoundException::new);

		if (!crntMemberUuid.equals(comment.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		comment.setContent(data.getContent());
		comment.setUpdDt(LocalDateTime.now());
		return PromptCommentResponse.from(comment);
	}

	/*
	본인 댓글인지 확인하고 삭제
	 */
	public void removePromptComment(Long commentId) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		PromptComment comment = promptCommentRepository.findById(commentId)
			.orElseThrow(PromptCommentNotFoundException::new);

		if (!crntMemberUuid.equals(comment.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		promptCommentRepository.delete(comment);
	}
	private MemberResponse getMemberInfo(UUID memberUuid) {

		log.info("DB로 회원 조회 중");
		log.info("userUuid = " + memberUuid);
		Member member = memberRepository.findByUserUuid(memberUuid);
		log.info("member = " + member);

		MemberResponse memberResponse = (null == member) ? new MemberResponse() : MemberResponse.from(member);

		return memberResponse;
	}
}
