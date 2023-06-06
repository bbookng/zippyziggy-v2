package com.zippyziggy.monolithic.talk.service;

import com.zippyziggy.monolithic.common.kafka.KafkaProducer;
import com.zippyziggy.monolithic.common.util.SecurityUtil;
import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.dto.response.WriterResponse;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import com.zippyziggy.monolithic.prompt.dto.request.TalkCntRequest;
import com.zippyziggy.monolithic.prompt.dto.response.PromptCardResponse;
import com.zippyziggy.monolithic.prompt.exception.ForbiddenMemberException;
import com.zippyziggy.monolithic.prompt.exception.PromptNotFoundException;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import com.zippyziggy.monolithic.prompt.repository.PromptBookmarkRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptCommentRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptLikeRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptRepository;
import com.zippyziggy.monolithic.talk.dto.request.TalkRequest;
import com.zippyziggy.monolithic.talk.dto.response.*;
import com.zippyziggy.monolithic.talk.exception.TalkNotFoundException;
import com.zippyziggy.monolithic.talk.model.Message;
import com.zippyziggy.monolithic.talk.model.Role;
import com.zippyziggy.monolithic.talk.model.Talk;
import com.zippyziggy.monolithic.talk.model.TalkLike;
import com.zippyziggy.monolithic.talk.repository.MessageRepository;
import com.zippyziggy.monolithic.talk.repository.TalkCommentRepository;
import com.zippyziggy.monolithic.talk.repository.TalkLikeRepository;
import com.zippyziggy.monolithic.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TalkService {

	private final TalkRepository talkRepository;
	private final TalkLikeRepository talkLikeRepository;
	private final PromptRepository promptRepository;
	private final PromptCommentRepository promptCommentRepository;
	private final PromptBookmarkRepository promptBookmarkRepository;
	private final PromptLikeRepository promptLikeRepository;
	private final MessageRepository messageRepository;
	private final TalkCommentRepository talkCommentRepository;
	private final KafkaProducer kafkaProducer;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;


	private static final String VIEWCOOKIENAME = "alreadyViewCookie";

	// 은지가 짤거임, 지금 검색 안됨 그냥 전체 조회
	public List<TalkListResponse> getTalkList(String crntMemberUuid) {
		return getTalks(talkRepository.findAll());
	}

	// promptUuid가 있는지 없는지에 따라 -> 프롬프트 활용 or 그냥 대화
	public TalkResponse createTalk(TalkRequest data, UUID crntMemberUuid) {
		Talk talk = Talk.from(data, crntMemberUuid);
		talkRepository.save(talk);
		List<Message> messageList = data.getMessages().stream().map(message -> Message.from(message, talk)).collect(
				Collectors.toList());
		talk.setMessages(messageList);
		if (data.getPromptUuid() != null) {
			talk.setPrompt(promptRepository
				.findByPromptUuidAndStatusCode(UUID.fromString(data.getPromptUuid()), StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new));
		}

		// 생성 시 search 서비스에 Elasticsearch INSERT 요청
		kafkaProducer.sendTalkCreateMessage("create-talk-topic", talk.toEsTalkRequest());

		return talk.toTalkResponse();
	}

	public TalkDetailResponse getTalkDetail(Long talkId, Pageable pageable) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Talk talk = talkRepository.findById(talkId).orElseThrow(TalkNotFoundException::new);

		Long likeCnt = talkLikeRepository.countAllByTalkId(talkId);
		boolean isLiked;

		if (crntMemberUuid.equals("defaultValue")) {
			isLiked = false;
		} else {
			isLiked = talkLikeRepository.existsByTalk_IdAndMemberUuid(talkId, crntMemberUuid);
		}


		MemberResponse memberResponse = getMemberInfo(talk.getMemberUuid());

		TalkDetailResponse response = talk.toDetailResponse(isLiked, likeCnt, memberResponse);

		if (talk.getPrompt() != null) {
			Prompt originPrompt = talk.getPrompt();

			MemberResponse originMember = getMemberInfo(originPrompt.getMemberUuid());

			PromptCardResponse promptCardResponse = getPromptCardResponse(crntMemberUuid.toString(), originPrompt, originMember);

			List<TalkListResponse> talkListResponses = getTalkListResponses(originPrompt, pageable);

			response.setOriginMember(originMember);
			response.setOriginPrompt(promptCardResponse);
			response.setTalkList(talkListResponses);
		}
		return response;

	}

	private PromptCardResponse getPromptCardResponse(String crntMemberUuid, Prompt originPrompt, MemberResponse originMember) {
		long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(originPrompt.getPromptUuid());
		long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(originPrompt.getPromptUuid(), StatusCode.OPEN);
		long talkCnt = talkRepository.countAllByPromptPromptUuid(originPrompt.getPromptUuid());

		// 좋아요, 북마크 여부
		boolean isOriginLiked;
		boolean isBookmarked;

		// 현재 로그인된 사용자가 아니면 기본값 false
		if (crntMemberUuid.equals("defaultValue")) {
			isBookmarked = false;
			isOriginLiked = false;
		} else {
			isBookmarked = promptBookmarkRepository.findByMemberUuidAndPrompt(UUID.fromString(crntMemberUuid), originPrompt).isPresent();
			isOriginLiked =  promptLikeRepository.findByPromptAndMemberUuid(originPrompt, UUID.fromString(crntMemberUuid)).isPresent();
		}

		PromptCardResponse promptCardResponse = PromptCardResponse
				.from(originMember, originPrompt, commentCnt, forkCnt, talkCnt, isBookmarked, isOriginLiked);
		return promptCardResponse;
	}

	public List<TalkListResponse> getTalkListResponses(Prompt originPrompt,  Pageable pageable) {


		List<Talk> talks = talkRepository.findAllByPromptPromptUuid(originPrompt.getPromptUuid(), pageable).toList();

		List<TalkListResponse> talkListResponses = getTalks(talks);

		return talkListResponses;
	}

	public List<TalkListResponse> getTalks(List<Talk> talks) {
		List<TalkListResponse> talkListResponses = talks.stream().map(t -> {
			String crntMemberUuid = securityUtil.getCurrentMember().getUserUuid().toString();

			boolean isTalkLiked;

			if (crntMemberUuid.equals("defaultValue")) {
				isTalkLiked = false;
			} else {
				isTalkLiked = talkLikeRepository
						.findByTalk_IdAndMemberUuid(t.getId(), UUID.fromString(crntMemberUuid)) != null ? true : false;
			}
			Long talkLikeCnt = talkLikeRepository.countAllByTalkId(t.getId());
			Long talkCommentCnt = talkCommentRepository.countAllByTalk_Id(t.getId());
			String question = messageRepository.findFirstByTalkIdAndRole(t.getId(), Role.USER).getContent().toString();
			String answer = messageRepository.findFirstByTalkIdAndRole(t.getId(), Role.ASSISTANT).getContent().toString();
			MemberResponse memberResponse = getMemberInfo(t.getMemberUuid());

			return TalkListResponse.from(
				t.getId(),
				t.getTitle(),
				question,
				answer,
				memberResponse,
				talkLikeCnt,
				talkCommentCnt,
				isTalkLiked);

		}).collect(Collectors.toList());
		return talkListResponses;
	}

	public void removeTalk(Long talkId) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Talk talk = talkRepository.findById(talkId)
				.orElseThrow(TalkNotFoundException::new);

		if (!crntMemberUuid.equals(talk.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		// 삭제 시 search 서비스에 Elasticsearch DELETE 요청
		kafkaProducer.sendTalkDeleteMessage("delete-talk-topic", talk.getId());

		talkRepository.delete(talk);
	}

	public void likeTalk(Long talkId, UUID crntMemberUuid) {
		// 좋아요 상태
		Boolean talkLikeExist = talkLikeRepository.existsByTalk_IdAndMemberUuid(talkId, crntMemberUuid);

		Talk talk = talkRepository.findById(talkId)
			.orElseThrow(TalkNotFoundException::new);

		if (!talkLikeExist) {
			// 톡 좋아요 조회
			TalkLike talkLike = TalkLike.builder()
				.talk(talk)
				.memberUuid(crntMemberUuid)
				.regDt(LocalDateTime.now()).build();

			// 톡 - 사용자 좋아요 관계 생성
			talkLikeRepository.save(talkLike);

			// 프롬프트 좋아요 개수 1 증가
			talk.setLikeCnt(talk.getLikeCnt() + 1);
			talkRepository.save(talk);

		} else {

			// 프롬프트 - 사용자 좋아요 취소
			TalkLike talkLike = talkLikeRepository
				.findByTalk_IdAndMemberUuid(talkId, crntMemberUuid)
				.orElseThrow(TalkNotFoundException::new);
			talkLikeRepository.delete(talkLike);

			// 프롬프트 좋아요 개수 1 감소
			talk.setLikeCnt(talk.getLikeCnt() - 1);
			talkRepository.save(talk);
		}

		// Elasticsearch에 좋아요 수 반영
		TalkCntRequest talkCntRequest = new TalkCntRequest(talkId, talk.getLikeCnt());
		kafkaProducer.sendTalkCnt("sync-talk-like-cnt", talkCntRequest);

	}

    public Long findCommentCnt(Long talkId) {
		return talkCommentRepository.countAllByTalk_Id(talkId);
    }

	public int updateHit(Long talkId, HttpServletRequest request, HttpServletResponse response) {

		Talk talk = talkRepository.findById(talkId).orElseThrow(TalkNotFoundException::new);

		Cookie[] cookies = request.getCookies();
		boolean checkCookie = false;
		int result = 0;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				// 이미 조회를 한 경우 체크
				if (cookie.getName().equals(VIEWCOOKIENAME+talkId)) checkCookie = true;

			}
			if(!checkCookie){
				Cookie newCookie = createCookieForForNotOverlap(talkId);
				response.addCookie(newCookie);
				result = talkRepository.updateHit(talkId);
			}
		} else {
			Cookie newCookie = createCookieForForNotOverlap(talkId);
			response.addCookie(newCookie);
			result = talkRepository.updateHit(talkId);
		}

		// Elasticsearch에 조회수 반영
		final TalkCntRequest talkCntRequest = new TalkCntRequest(talkId, talk.getHit());
		kafkaProducer.sendTalkCnt("sync-talk-hit", talkCntRequest);

		return result;
	}

	/*
	 * 조회수 중복 방지를 위한 쿠키 생성 메소드
	 * @param cookie
	 * @return
	 * */
	private Cookie createCookieForForNotOverlap(Long talkId) {
		Cookie cookie = new Cookie(VIEWCOOKIENAME+talkId, String.valueOf(talkId));
		cookie.setComment("조회수 중복 증가 방지 쿠키");    // 쿠키 용도 설명 기재
		cookie.setMaxAge(getRemainSecondForTomorrow());     // 하루를 준다.
		cookie.setHttpOnly(true);                // 서버에서만 조작 가능
		return cookie;
	}

	// 다음 날 정각까지 남은 시간(초)
	private int getRemainSecondForTomorrow() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tomorrow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
		return (int) now.until(tomorrow, ChronoUnit.SECONDS);
	}

    public MemberTalkList findTalksByMemberUuid(
		String crntMemberUuid, int page, int size, String sort
	) {
		Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
		Pageable pageable = PageRequest.of(page, size, sortBy);


		Page<Talk> pagedTalk = talkRepository.findTalksByMemberUuid(UUID.fromString(crntMemberUuid), pageable);
		long totalTalksCnt = pagedTalk.getTotalElements();
		int totalPageCnt = pagedTalk.getTotalPages();

		List<MemberTalk> memberTalkList = new ArrayList<>();
		for (Talk talk : pagedTalk) {
			Long talkId = talk.getId();

			// MemberClient에 memberUuid로 요청
			MemberResponse member = getMemberInfo(talk.getMemberUuid());
			WriterResponse writer = member.toWriterResponse();

			Long commentCnt = findCommentCnt(talkId);

			memberTalkList.add(MemberTalk.of(writer, talk, commentCnt));
		}
		return new MemberTalkList(totalTalksCnt, totalPageCnt, memberTalkList);
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
