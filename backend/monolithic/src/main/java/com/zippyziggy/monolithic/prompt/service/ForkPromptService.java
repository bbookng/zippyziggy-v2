package com.zippyziggy.monolithic.prompt.service;

import com.zippyziggy.monolithic.common.aws.AwsS3Uploader;
import com.zippyziggy.monolithic.common.kafka.KafkaProducer;
import com.zippyziggy.monolithic.common.util.RedisUtils;
import com.zippyziggy.monolithic.common.util.SecurityUtil;
import com.zippyziggy.monolithic.member.dto.response.MemberResponse;
import com.zippyziggy.monolithic.member.model.Member;
import com.zippyziggy.monolithic.member.repository.MemberRepository;
import com.zippyziggy.monolithic.prompt.dto.request.PromptRequest;
import com.zippyziggy.monolithic.prompt.dto.response.ForkPromptResponse;
import com.zippyziggy.monolithic.prompt.dto.response.ForkedPromptListResponse;
import com.zippyziggy.monolithic.prompt.dto.response.PromptCardResponse;
import com.zippyziggy.monolithic.prompt.model.Prompt;
import com.zippyziggy.monolithic.prompt.model.StatusCode;
import com.zippyziggy.monolithic.prompt.repository.PromptBookmarkRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptCommentRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptLikeRepository;
import com.zippyziggy.monolithic.prompt.repository.PromptRepository;
import com.zippyziggy.monolithic.talk.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ForkPromptService {

	private final AwsS3Uploader awsS3Uploader;
	private final PromptRepository promptRepository;
	private final PromptCommentRepository promptCommentRepository;
	private final PromptBookmarkRepository promptBookmarkRepository;
	private final PromptLikeRepository promptLikeRepository;
	private final TalkRepository talkRepository;
	private final KafkaProducer kafkaProducer;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;
	private final RedisUtils redisUtils;

	public ForkPromptResponse createForkPrompt(UUID promptUuid, PromptRequest data, MultipartFile thumbnail) {

		String thumbnailUrl = awsS3Uploader.upload(thumbnail, "thumbnails");

		Prompt prompt = Prompt.from(data, securityUtil.getCurrentMember().getUserUuid(), thumbnailUrl);
		prompt.setOriginPromptUuid(promptUuid);

		promptRepository.save(prompt);

		kafkaProducer.send("create-prompt-topic", prompt.toEsPromptRequest());

		return ForkPromptResponse.from(prompt);
	}

	public ForkedPromptListResponse getForkedPromptList(UUID promptUuid, Pageable pageable) {


		Long forkPromptCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN);

		Page<Prompt> forkedPrompts = promptRepository.findAllByOriginPromptUuidAndStatusCode(promptUuid, StatusCode.OPEN, pageable);

		// fork 프롬프트들 카드 정보 가져오는 메서드
		List<PromptCardResponse> prompts = getForkedPromptResponses(forkedPrompts);

		return new ForkedPromptListResponse(forkPromptCnt, prompts);
	}

	private List<PromptCardResponse> getForkedPromptResponses(Page<Prompt> forkedPrompts) {

		List<PromptCardResponse> promptDtoList = forkedPrompts.stream().map(prompt -> {

			UUID crntMemberUuid = securityUtil.getCurrentMember().getUserUuid();
			MemberResponse writerInfo = getMemberInfo(prompt.getMemberUuid());

			// 댓글, 포크 프롬프트의 포크 수, 대화 수 가져오기
			long commentCnt = promptCommentRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());
			long forkCnt = promptRepository.countAllByOriginPromptUuidAndStatusCode(prompt.getPromptUuid(), StatusCode.OPEN);
			long talkCnt = talkRepository.countAllByPromptPromptUuid(prompt.getPromptUuid());

			// 좋아요, 북마크 여부
			boolean isLiked;
			boolean isBookmarked;

			// 현재 로그인된 사용자가 아니면 기본값 false
			if (crntMemberUuid.toString().equals("defaultValue")) {
				isBookmarked = false;
				isLiked = false;
			} else {
				isBookmarked = promptBookmarkRepository.findByMemberUuidAndPrompt(crntMemberUuid, prompt).isPresent();
				isLiked =  promptLikeRepository.findByPromptAndMemberUuid(prompt, crntMemberUuid).isPresent();
			}

			// DTO 로 변환
			PromptCardResponse promptDto = PromptCardResponse
				.from(writerInfo, prompt, commentCnt, forkCnt, talkCnt, isBookmarked, isLiked);

			return promptDto;

		}).collect(Collectors.toList());

		return promptDtoList;
	}
	private MemberResponse getMemberInfo(UUID memberUuid) {
		if (redisUtils.isExists("member" + memberUuid)) {
			log.info("redis로 회원 조회 중");
			MemberResponse memberResponse = redisUtils.get("member" + memberUuid, MemberResponse.class);
			return memberResponse;

		} else {
			log.info("DB로 회원 조회 중");
			log.info("userUuid = " + memberUuid);
			Member member = memberRepository.findByUserUuid(memberUuid);
			log.info("member = " + member);

			MemberResponse memberResponse = (null == member) ? new MemberResponse() : MemberResponse.from(member);

			return memberResponse;
		}
	}
}
