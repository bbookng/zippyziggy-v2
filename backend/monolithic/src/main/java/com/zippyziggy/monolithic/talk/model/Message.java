package com.zippyziggy.monolithic.talk.model;

import com.zippyziggy.monolithic.talk.dto.request.MessageRequest;
import com.zippyziggy.monolithic.talk.dto.response.MessageResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class Message {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "talk_id", nullable = false)
	private Talk talk;

	@Column(nullable = false)
	private Role role;

	@Lob
	@Column(nullable = false)
	private String content;

	public static Message from(MessageRequest data, Talk talk) {
		return  Message.builder()
			.talk(talk)
			.role(data.getRole())
			.content(data.getContent())
			.build();
	}

	public MessageResponse toMessageResponse() {
		return new MessageResponse(this.getRole().getDescription().toUpperCase(), this.getContent());
	}

}
