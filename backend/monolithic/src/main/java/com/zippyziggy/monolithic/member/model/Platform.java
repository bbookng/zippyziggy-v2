package com.zippyziggy.monolithic.member.model;

public enum Platform {
	KAKAO("kakao"), GOOGLE("google");

	private final String platform;

	Platform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}
}
