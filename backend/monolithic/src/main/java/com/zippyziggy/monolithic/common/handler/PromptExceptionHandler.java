//package com.zippyziggy.monolithic.common.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@Slf4j
//@RestControllerAdvice("com.zippyziggy")
//public class PromptExceptionHandler {
//
//	@ExceptionHandler(PromptNotFoundException.class)
//	public ResponseEntity<BaseResponseBody> handlePromptNotFoundException(PromptNotFoundException e) {
//		log.info("PromptNotFoundException", e.getMessage());
//		e.printStackTrace();
//		return ResponseEntity.badRequest()
//			.body(BaseResponseBody.of(e.getMessage()));
//	}
//
//	@ExceptionHandler(PromptCommentNotFoundException.class)
//	public ResponseEntity<BaseResponseBody> handlePromptCommentNotFoundException(PromptCommentNotFoundException e) {
//		log.info("PromptNotFoundException", e.getMessage());
//		e.printStackTrace();
//		return ResponseEntity.badRequest()
//			.body(BaseResponseBody.of(e.getMessage()));
//	}
//
//	@ExceptionHandler(MemberNotFoundException.class)
//	public ResponseEntity<BaseResponseBody> handleMemberNotFoundException(MemberNotFoundException e) {
//		log.info("MemberNotFoundException", e.getMessage());
//		e.printStackTrace();
//		return ResponseEntity.badRequest()
//			.body(BaseResponseBody.of(e.getMessage()));
//	}
//
//	@ExceptionHandler(ForbiddenMemberException.class)
//	public ResponseEntity<BaseResponseBody> handleForbiddenMemberException(ForbiddenMemberException e) {
//		log.info("ForbiddenMemberException", e.getMessage());
//		e.printStackTrace();
//		return ResponseEntity.badRequest()
//			.body(BaseResponseBody.of(e.getMessage()));
//	}
//
//	@ExceptionHandler(AwsUploadException.class)
//	public ResponseEntity<BaseResponseBody> handleAwsUploadException(AwsUploadException e) {
//		log.info("AwsUploadException", e.getMessage());
//		e.printStackTrace();
//		return ResponseEntity.badRequest()
//			.body(BaseResponseBody.of(e.getMessage()));
//	}
//
//
//}
