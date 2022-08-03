package umc.spring.ringleader.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청성공

     */
    SUCCESS(true,1000,"요청에 성공하였습니다."),

    /**
     * 2000 : User관련 오류
     */
    //Common
    POST_Login_EMPTY_EMAIL(false,2001,"이메일을 다시 입력해주세요."),
    POST_Login_EMPTY_PWD(false,2002,"패스워드를 다시 입력해주세요."),
    POST_SIGNUP_EMPTY_EMAIL(false,2003,"이메일을 다시 입력해주세요."),
    POST_SIGNUP_INVALID_EMAIL(false,2004,"잘못된 이메일 형식입니다."),
    POST_SIGNUP_MISMATCH_PASSWORD(false,2005,"비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    NOT_PROVIDE_LOGIN_SERVICE(false,2006,"제공하지 않는 로그인 서비스입니다."),


    /**
     * 3000 : Review관련 오류
     */
    REVIEW_POST_TITLE_IS_NULL(false,3001,"리뷰 제목은 필수 항목 입니다."),
    REVIEW_POST_HASHTAG_IS_NULL(false,3002,"최소 1개 이상의 해시태그를 포함하여야 합니다"),
    REVIEW_POST_CONTENT_IS_NULL(false,3003,"내용은 필수 항목 입니다."),

    REVIEW_POST_CATEGORY_IS_NULL(false,3004,"카테고리 선택은 필수 입니다."),
    /**
     * 5000 : Region관련 오류
     */

    /**
     * 6000 : Database, Server 오류
     */

    REVIEW_NULL(false, 6001, "존재하지 않는 리뷰입니다");





    private final boolean isSuccess;

    private final int code;
    private final String message;
    
    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
