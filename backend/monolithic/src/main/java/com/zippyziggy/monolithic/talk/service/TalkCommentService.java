package com.zippyziggy.monolithic.talk.service;

import com.zippyziggy.monolithic.common.util.SecurityUtil;
import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import com.zippyziggy.monolithic.prompt.exception.ForbiddenMemberException;
import com.zippyziggy.monolithic.talk.dto.request.TalkCommentRequest;
import com.zippyziggy.monolithic.talk.dto.response.TalkCommentListResponse;
import com.zippyziggy.monolithic.talk.dto.response.TalkCommentResponse;
import com.zippyziggy.monolithic.talk.exception.TalkCommentNotFoundException;
import com.zippyziggy.monolithic.talk.exception.TalkNotFoundException;
import com.zippyziggy.monolithic.talk.model.Talk;
import com.zippyziggy.monolithic.talk.model.TalkComment;
import com.zippyziggy.monolithic.talk.repository.TalkCommentRepository;
import com.zippyziggy.monolithic.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TalkCommentService {

	private final TalkCommentRepository talkCommentRepository;
	private final TalkRepository talkRepository;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;

	public TalkCommentListResponse getTalkCommentList(Long talkId, Pageable pageable) {
		final Page<TalkComment> commentList = talkCommentRepository.findAllByTalk_Id(talkId, pageable);

		final List<TalkCommentResponse> talkCommentResponseList = commentList.stream().map(comment -> {
			MemberResponse writerInfo = getMemberInfo(comment.getMemberUuid());
			return TalkCommentResponse.from(comment, writerInfo);
		}).collect(Collectors.toList());

		Long commentCnt = talkCommentRepository.countAllByTalk_Id(talkId);

		return new TalkCommentListResponse(commentCnt, talkCommentResponseList);
	}

	public TalkCommentResponse createTalkComment(Long talkId, TalkCommentRequest data) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Talk talk = talkRepository.findById(talkId).orElseThrow(TalkNotFoundException::new);

		TalkComment talkComment = TalkComment.from(data, crntMemberUuid, talk);
		talkCommentRepository.save(talkComment);

		MemberResponse talkCommentMember = getMemberInfo(talkComment.getMemberUuid());

		return TalkCommentResponse.from(talkComment, talkCommentMember);
	}

	/*
	본인 댓글인지 확인하고 수정
	 */
	public TalkCommentResponse modifyTalkComment(Long commentId, TalkCommentRequest data) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		TalkComment comment = talkCommentRepository.findById(commentId)
			.orElseThrow(TalkCommentNotFoundException::new);

		if (!crntMemberUuid.equals(comment.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		final MemberResponse talkCommentMember = getMemberInfo(comment.getMemberUuid());

		comment.setContent(data.getContent());
		return TalkCommentResponse.from(comment, talkCommentMember);
	}

	/*
	본인 댓글인지 확인하고 삭제
	 */
	public void removeTalkComment(Long commentId) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		TalkComment comment = talkCommentRepository.findById(commentId)
			.orElseThrow(TalkCommentNotFoundException::new);

		if (!crntMemberUuid.equals(comment.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		talkCommentRepository.delete(comment);
	}

	private MemberResponse getMemberInfo(UUID memberUuid) {

		log.info("DB로 회원 조회 중");
		log.info("userUuid = " + memberUuid);
		Member member = memberRepository.findByUserUuid(memberUuid);
		log.info("member = " + member);

		MemberResponse memberResponse = (null == member)
				? new MemberResponse("알 수 없음", "https://zippyziggy.s3.ap-northeast-2.amazonaws.com/default/noProfile.png")
				: MemberResponse.from(member);
		return memberResponse;
	}
}
