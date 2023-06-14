package com.zippyziggy.monolithic.prompt.service;

import com.zippyziggy.monolithic.common.aws.AwsS3Uploader;
import com.zippyziggy.monolithic.common.util.SecurityUtil;
import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import com.zippyziggy.monolithic.notice.service.AlarmService;
import com.zippyziggy.monolithic.prompt.dto.request.*;
import com.zippyziggy.monolithic.prompt.dto.response.*;
import com.zippyziggy.monolithic.prompt.exception.*;
import com.zippyziggy.monolithic.prompt.model.*;
import com.zippyziggy.monolithic.prompt.repository.*;
import com.zippyziggy.monolithic.talk.dto.response.PromptTalkListResponse;
import com.zippyziggy.monolithic.talk.dto.response.TalkListResponse;
import com.zippyziggy.monolithic.talk.repository.TalkRepository;
import com.zippyziggy.monolithic.talk.service.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PromptService{


	private static final String VIEWCOOKIENAME = "alreadyViewCookie";
	private static final String MODEL = "gpt-3.5-turbo";
	private static final String URL = "https://api.openai.com/v1/chat/completions";

	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;

	private final AwsS3Uploader awsS3Uploader;

	private final PromptRepository promptRepository;
	private final PromptLikeRepository promptLikeRepository;
	private final PromptBookmarkRepository promptBookmarkRepository;
	private final PromptCommentRepository promptCommentRepository;
	private final TalkService talkService;
	private final TalkRepository talkRepository;
	private final RatingRepository ratingRepository;
	private final PromptReportRepository promptReportRepository;
	private final PromptClickRepository promptClickRepository;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;
	private final AlarmService alarmService;


	// Exception 처리 필요
	public PromptResponse createPrompt(PromptRequest data, MultipartFile thumbnail) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		String thumbnailUrl;

		if (thumbnail == null) {
			thumbnailUrl = "default thumbnail image";
		} else {
			thumbnailUrl = awsS3Uploader.upload(thumbnail, "thumbnails");
		}

		Prompt prompt = Prompt.from(data, crntMemberUuid, thumbnailUrl);

		promptRepository.save(prompt);

		return PromptResponse.from(prompt);
	}

	public PromptResponse modifyPrompt(UUID promptUuid, PromptRequest data, MultipartFile thumbnail) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);

		if (!crntMemberUuid.equals(prompt.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		// 기존 thumbnail 지우기
		if (thumbnail == null) {
			try {
				awsS3Uploader.delete("thumbnails/", prompt.getThumbnail());
			} catch (RuntimeException e) {
				throw new AwsUploadException("삭제하는데 실패하였습니다.");
			}
			prompt.setThumbnail("https://zippyziggy.s3.ap-northeast-2.amazonaws.com/default/noCardImg.png");

		} else {
			awsS3Uploader.delete("thumbnails/", prompt.getThumbnail());
			String thumbnailUrl = awsS3Uploader.upload(thumbnail, "thumbnail");
			prompt.setThumbnail(thumbnailUrl);
		}

		prompt.setTitle(data.getTitle());
		prompt.setDescription(data.getDescription());
		prompt.setCategory(data.getCategory());
		prompt.setUpdDt(LocalDateTime.now());
		prompt.setPrefix(data.getMessage().getPrefix());
		prompt.setExample(data.getMessage().getExample());
		prompt.setSuffix(data.getMessage().getSuffix());

		return PromptResponse.from(prompt);
	}

	public int updateHit(UUID promptUuid, HttpServletRequest request, HttpServletResponse response) {

		final Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);
		final Long promptId = prompt.getId();

		Cookie[] cookies = request.getCookies();
		boolean checkCookie = false;
		int result = 0;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				// 이미 조회를 한 경우 체크
				if (cookie.getName().equals(VIEWCOOKIENAME+promptId)) checkCookie = true;

			}
			if(!checkCookie){
				Cookie newCookie = createCookieForForNotOverlap(promptId);
				response.addCookie(newCookie);
				result = promptRepository.updateHit(promptId);
			}
		} else {
			Cookie newCookie = createCookieForForNotOverlap(promptId);
			response.addCookie(newCookie);
			result = promptRepository.updateHit(promptId);
		}

		return result;
	}

	/*
	 * 조회수 중복 방지를 위한 쿠키 생성 메소드
	 * @param cookie
	 * @return
	 * */
	private Cookie createCookieForForNotOverlap(Long promptId) {
		Cookie cookie = new Cookie(VIEWCOOKIENAME+promptId, String.valueOf(promptId));
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

	public PromptDetailResponse getPromptDetail(UUID promptUuid) {
		Member currentMember = securityUtil.getCurrentMember();
		String crntMemberUuid = (currentMember != null) ? currentMember.getUserUuid().toString() : null;

		final Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);

		boolean isLiked;
		boolean isBookmarked;

		if (crntMemberUuid == null) {
			isLiked = false;
			isBookmarked = false;
		} else {
			isBookmarked = promptBookmarkRepository.findByMemberUuidAndPrompt(UUID.fromString(crntMemberUuid), prompt).isPresent();
			isLiked =  promptLikeRepository.findByPromptAndMemberUuid(prompt, UUID.fromString(crntMemberUuid)).isPresent();
		}

		PromptDetailResponse promptDetailResponse = prompt.toDetailResponse(isLiked, isBookmarked);

		MemberResponse writerInfo = getWriterInfo(prompt.getMemberUuid());

		promptDetailResponse.setWriter(writerInfo.toWriterResponse());

		// 원본 id가 현재 프롬프트 아이디와 같지 않으면 포크된 프롬프트
		if (prompt.isForked()) {
			UUID originalMemberUuid = promptRepository
					.findByOriginPromptUuidAndPromptUuid(prompt.getOriginPromptUuid(), promptUuid)
					.orElseThrow(PromptNotFoundException::new)
					.getMemberUuid();

			// 탈퇴한 사용자일 시에 예외를 던지지 않고, 빈 객체를 보내서 사용자 정보 없음으로 표시
			MemberResponse originalMemberInfo;
			try {
				originalMemberInfo = getMemberInfo(originalMemberUuid);
			} catch (RuntimeException e) {
				originalMemberInfo = new MemberResponse();
			}

			UUID originPromptUuid = prompt.getOriginPromptUuid();

			promptDetailResponse.setOriginer(originalMemberInfo.toOriginerResponse());
			promptDetailResponse.setOriginPromptUuid(originPromptUuid);
			promptDetailResponse.setOriginPromptTitle(promptRepository
					.findByPromptUuid(originPromptUuid)
					.orElseThrow(PromptNotFoundException::new)
					.getTitle());
		}

		if (crntMemberUuid != null) {
			// 프롬프트 조회 시 최근 조회 테이블에 추가
			final PromptClick promptClick = PromptClick.from(prompt, UUID.fromString(crntMemberUuid));
			promptClickRepository.save(promptClick);
		}

		return promptDetailResponse;
	}


	public PromptTalkListResponse getPromptTalkList(UUID promptUuid, Pageable pageable) {
		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);
		List<TalkListResponse> talkListResponses = talkService.getTalkListResponses(prompt, pageable);
		return new PromptTalkListResponse(talkListResponses.size(), talkListResponses);
	}

    /*
	본인이 작성한 프롬프트인지 확인 필요
	 */

	public void removePrompt(String promptUuid) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(UUID.fromString(promptUuid), StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);

		if (!crntMemberUuid.equals(prompt.getMemberUuid())) {
			throw new ForbiddenMemberException();
		}

		awsS3Uploader.delete("thumbnails/", prompt.getThumbnail());
		prompt.setThumbnail(null);
		prompt.setStatusCode(StatusCode.DELETED);
		promptRepository.save(prompt);

	}

    /*
    프롬프트 좋아요 처리
     */

	public void likePrompt(UUID promptUuid) {
		String crntMemberUuid = securityUtil.getCurrentMember().getUserUuid().toString();

		// 좋아요 상태 추적
		PromptLike promptLikeExist = likePromptExist(promptUuid);

		// 좋아요를 이미 한 상태일 경우
		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);

		if (promptLikeExist == null) {
			// 프롬프트 조회

			PromptLike promptLike = PromptLike.builder()
					.prompt(prompt)
					.memberUuid(UUID.fromString(crntMemberUuid))
					.regDt(LocalDateTime.now()).build();

			// 프롬프트 - 사용자 좋아요 관계 생성
			promptLikeRepository.save(promptLike);

			// 프롬프트 좋아요 개수 1 증가
			prompt.setLikeCnt(prompt.getLikeCnt() + 1);
			promptRepository.save(prompt);

			alarmService.send(prompt.getMemberUuid().toString(),
					"'" + prompt.getTitle() + "'" + "게시물 좋아요 + 1",
					"https://zippyziggy.kr/prompts/" + prompt.getPromptUuid().toString());

		} else {

			// 프롬프트 - 사용자 좋아요 취소
			promptLikeRepository.delete(promptLikeExist);

			// 프롬프트 좋아요 개수 1 감소
			prompt.setLikeCnt(prompt.getLikeCnt() - 1);
			promptRepository.save(prompt);
		}

	}

    /*
    로그인한 유저가 프롬프트를 좋아요 했는지 확인하는 로직
    null이 아니면 좋아요를 한 상태, null이면 좋아요를 하지 않은 상태
     */

	private PromptLike likePromptExist(UUID promptUuid) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();

		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);
		PromptLike promptLike = promptLikeRepository.findByPromptAndMemberUuid(prompt, crntMemberUuid).orElse(null);
		if (promptLike != null) {
			return promptLike;
		} else {
			return null;
		}
	}

    /*
    로그인한 유저가 좋아요를 누른 프롬프트 조회하기, PromptCard 타입의 리스트 형식으로 응답
     */
	public PromptCardListResponse likePromptsByMember (String userUuid, Pageable pageable) {

		UUID crntMemberUuid = UUID.fromString(userUuid);

		Page<Prompt> prompts = promptLikeRepository.findAllPromptsByMemberUuid(crntMemberUuid, pageable);
		final long totalPromptsCnt = prompts.getTotalElements();
		final int totalPageCnt = prompts.getTotalPages();
		List<PromptCardResponse> promptCardResponses = new ArrayList<>();

		for (Prompt prompt: prompts) {
			long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
			long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
			long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

			MemberResponse writerInfo = getWriterInfo(prompt.getMemberUuid());

			// 좋아요, 북마크 여부
			boolean isBookmarked = promptBookmarkRepository.findByMemberUuidAndPrompt(crntMemberUuid,prompt).isPresent();
			boolean isOriginLiked = promptLikeRepository.findByPromptAndMemberUuid(prompt, crntMemberUuid).isPresent();

			PromptCardResponse promptCardResponse = PromptCardResponse.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarked, isOriginLiked);
			promptCardResponses.add(promptCardResponse);
		}

		return PromptCardListResponse.from(totalPromptsCnt, totalPageCnt, promptCardResponses);

	}


	/*
    북마크 등록 및 삭제
     */
	public void bookmarkPrompt(UUID promptUuid) {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Prompt prompt = promptRepository
				.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
				.orElseThrow(PromptNotFoundException::new);
		PromptBookmark promptBookmark = promptBookmarkRepository.findByMemberUuidAndPrompt(crntMemberUuid, prompt).orElse(null);
		if (promptBookmark == null) {
			promptBookmarkRepository.save(PromptBookmark.from(prompt, crntMemberUuid));
		} else {
			promptBookmarkRepository.delete(promptBookmark);
		}
	}


	/*
    북마크 조회하기
     */
	public PromptCardListResponse bookmarkPromptByMember(String memberUuid, Pageable pageable) {

		UUID crntMemberUuid = UUID.fromString(memberUuid);

		Page<Prompt> prompts = promptBookmarkRepository.findAllPromptsByMemberUuid(crntMemberUuid, pageable);
		long totalPromptsCnt = prompts.getTotalElements();
		int totalPageCnt = prompts.getTotalPages();
		List<PromptCardResponse> promptCardResponses = new ArrayList<>();

		for (Prompt prompt : prompts) {
			long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
			long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
			long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

			MemberResponse writerInfo = getWriterInfo(prompt.getMemberUuid());

			boolean isBookmarded = promptBookmarkRepository.findByMemberUuidAndPrompt(crntMemberUuid, prompt).isPresent();
			boolean isLiked = promptLikeRepository.findByPromptAndMemberUuid(prompt, crntMemberUuid).isPresent();

			PromptCardResponse promptCardResponse = PromptCardResponse.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarded, isLiked);
			promptCardResponses.add(promptCardResponse);

		}
		return PromptCardListResponse.from(totalPromptsCnt, totalPageCnt, promptCardResponses);
	}

	/*
    북마크 조회하기(extension)
     */
	public PromptCardListExtensionResponse bookmarkPromptByMemberAndExtension(String crntMemberUuid, Pageable pageable) {
		log.info(String.valueOf(pageable.getSort()));

		Page<Prompt> prompts = promptBookmarkRepository.findAllPromptsByMemberUuid(UUID.fromString(crntMemberUuid), pageable);
		long totalPromptsCnt = prompts.getTotalElements();
		int totalPageCnt = prompts.getTotalPages();


		List<PromptBookmarkResponse> promptBookmarkResponses = new ArrayList<>();

		for (Prompt prompt : prompts) {
			long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
			long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
			long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

			Member member = memberRepository.findByUserUuid(prompt.getMemberUuid());
			log.info("member = " + member);

			MemberResponse writerInfo = (null == member)
					? new MemberResponse("알 수 없음", "https://zippyziggy.s3.ap-northeast-2.amazonaws.com/default/noProfile.png")
					: MemberResponse.from(member);

			boolean isBookmarked = promptBookmarkRepository.findByMemberUuidAndPrompt(UUID.fromString(crntMemberUuid), prompt) != null
					? true : false;
			boolean isLiked = promptLikeRepository.findByPromptAndMemberUuid(prompt, UUID.fromString(crntMemberUuid)) != null
					? true : false;

			PromptBookmarkResponse promptCardResponse = PromptBookmarkResponse.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarked, isLiked);
			promptBookmarkResponses.add(promptCardResponse);

		}

		return PromptCardListExtensionResponse.from(totalPromptsCnt, totalPageCnt, promptBookmarkResponses);

	}


	/*
    프롬프트 평가
     */
	public void ratingPrompt(UUID promptUuid, PromptRatingRequest promptRatingRequest) throws Exception {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Rating ratingExist = ratingRepository.findByMemberUuidAndPromptPromptUuid(crntMemberUuid, promptUuid);

		if (ratingExist == null) {
			Prompt prompt = promptRepository
					.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
					.orElseThrow(PromptNotFoundException::new);
			Rating rating = Rating.from(crntMemberUuid, prompt, promptRatingRequest.getScore());
			ratingRepository.save(rating);
		} else {
			throw new RatingAlreadyExistException();
		}
	}

	/*
    프롬프트 톡 및 댓글 개수 조회
     */
	public SearchPromptResponse searchPrompt(UUID promptUuid) {
		Member currentMember = securityUtil.getCurrentMember();
		String crntMemberUuid = (currentMember != null) ? currentMember.getUserUuid().toString() : null;

		final Prompt prompt = promptRepository
				.findByPromptUuid(promptUuid)
				.orElseThrow(PromptNotFoundException::new);

		long talkCnt = talkRepository.countAllByPromptPromptUuid(promptUuid);
		long commentCnt = promptCommentRepository
				.countAllByPromptPromptUuid(promptUuid);

		boolean isLiked;
		boolean isBookmarked;
		if (crntMemberUuid == null) {
			isLiked = false;
			isBookmarked = false;
		} else {
			UUID memberUuid = UUID.fromString(crntMemberUuid);
			isLiked = promptLikeRepository
					.existsByMemberUuidAndPrompt_PromptUuid(memberUuid, promptUuid);
			isBookmarked = promptBookmarkRepository
					.existsByMemberUuidAndPrompt_PromptUuid(memberUuid, promptUuid);
		}

		return SearchPromptResponse.from(prompt, talkCnt, commentCnt, isLiked, isBookmarked);
	}

	/*
    프롬프트 신고 접수 한 프롬프트 당 5개까지 작성가능
     */
	public void promptReport(UUID promptUuid, PromptReportRequest promptReportRequest) throws Exception {
		UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
		Long reportCnt = promptReportRepository.countAllByMemberUuidAndPrompt_PromptUuid(crntMemberUuid, promptUuid);
		if (reportCnt <= 5 ) {
			Prompt prompt = promptRepository
					.findByPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN)
					.orElseThrow(PromptNotFoundException::new);
			PromptReport promptReport = PromptReport.from(crntMemberUuid, prompt, promptReportRequest.getContent());
			promptReportRepository.save(promptReport);

			alarmService.send(prompt.getMemberUuid().toString(),
					"'" + prompt.getTitle() + "'" + "게시물이 신고되었습니다.",
					"zippyziggy.kr/prompts/" + prompt.getPromptUuid().toString());

		} else {
			throw new ReportAlreadyExistException();
		}
	}

	/*
    프롬프트 신고 내용 확인
     */
	public Page<PromptReportResponse> reports(Pageable pageable) {
		Page<PromptReport> reports = promptReportRepository.findAllByOrderByRegDtDesc(pageable);
		Page<PromptReportResponse> promptReportResponse = PromptReportResponse.from(reports);
		return promptReportResponse;
	}

	/*
    최근 조회한 프롬프트 5개 조회
     */
	public List<PromptCardResponse> recentPrompts(String crntMemberUuid) {
		if (crntMemberUuid.equals("defaultValue")) {
			return null;
		} else {

			List<PromptClick> promptClicks = promptClickRepository
					.findTop5DistinctByMemberUuidAndPrompt_StatusCodeOrderByRegDtDesc(UUID.fromString(crntMemberUuid), StatusCode.OPEN);

			// 해당 프롬프트 내용 가져오기
			List<Prompt> prompts = new ArrayList<>();
			for (PromptClick promptClick: promptClicks) {
				prompts.add(promptClick.getPrompt());
			}


			List<PromptCardResponse> promptCardResponses = new ArrayList<>();

			// PromptCardResponse Dto로 변환
			for (Prompt prompt : prompts) {
				long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
				long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
				long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

				MemberResponse writerInfo = getWriterInfo(prompt.getMemberUuid());

				boolean isBookmarded = promptBookmarkRepository.findByMemberUuidAndPrompt(UUID.fromString(crntMemberUuid), prompt).isPresent();
				boolean isLiked = promptLikeRepository.findByPromptAndMemberUuid(prompt, UUID.fromString(crntMemberUuid)).isPresent();

				PromptCardResponse promptCardResponse = PromptCardResponse.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarded, isLiked);
				promptCardResponses.add(promptCardResponse);
			}
			return promptCardResponses;
		}
	}

	/*
    memberUuid로 프롬프트 조회
     */
	public PromptCardListResponse memberPrompts(String crntMemberUuid, Pageable pageable) {
		Page<Prompt> prompts = promptRepository.findAllByMemberUuidAndStatusCode(UUID.fromString(crntMemberUuid), StatusCode.OPEN, pageable);
		long totalPromptsCnt = prompts.getTotalElements();
		int totalPageCnt = prompts.getTotalPages();

		List<PromptCardResponse> promptCardResponses = new ArrayList<>();

		for (Prompt prompt : prompts) {
			long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
			long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
			long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

			MemberResponse writerInfo = getWriterInfo(prompt.getMemberUuid());

			boolean isBookmarded = promptBookmarkRepository.findByMemberUuidAndPrompt(UUID.fromString(crntMemberUuid), prompt).isPresent();
			boolean isLiked = promptLikeRepository.findByPromptAndMemberUuid(prompt, UUID.fromString(crntMemberUuid)).isPresent();

			PromptCardResponse promptCardResponse = PromptCardResponse.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarded, isLiked);
			promptCardResponses.add(promptCardResponse);
		}

		return PromptCardListResponse.from(totalPromptsCnt, totalPageCnt, promptCardResponses);
	}

	public GptApiResponse getChatGptAnswer(AppChatGptRequest data) {
		// create a request
		ChatGptRequest request = new ChatGptRequest(MODEL, data);

		// call the API
		ChatGptResponse response = restTemplate.postForObject(URL, request, ChatGptResponse.class);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			return new GptApiResponse("No response");
		}

		// return the first response
		final String answer = response.getChoices().get(0).getMessage().getContent();
		return new GptApiResponse(answer);
	}


	private MemberResponse getWriterInfo(UUID memberUuid) {
		MemberResponse writerInfo;
		try {
			writerInfo = getMemberInfo(memberUuid);
			log.info("member에서 예외 처리 없이 찾아왔지만 null임");
		} catch (RuntimeException e) {
			writerInfo = new MemberResponse();
			log.info("member에서 예외 떴음");
		}
		return writerInfo;
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
