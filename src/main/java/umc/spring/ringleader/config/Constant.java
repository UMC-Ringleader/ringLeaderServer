package umc.spring.ringleader.config;

public class Constant {

	//DB 결과 조회
	public static final int NULL_DATA_CODE = 0;

	//Action별 Contribution 반영치
	public static final int POST_IMG_REVIEW = 10;
	public static final int POST_NOT_IMG_REVIEW = 5;

	public static final int DELETE_IMG_REVIEW = -10;
	public static final int DELETE_NOT_IMG_REVIEW = -5;
	public static final int ADD_FEEDBACK = 1;
	public static final int DELETE_FEEDBACK = -1;

	public static final int DAILY_FIRST_LOGIN_COMPENSATION = 1;

	// Feedback 에 따른 Contribution
	public static final int INCENTIVE_DATE_FLAG = 4;
	public static final int INCENTIVE_VALID_FEEDBACK_COUNT = 10;
	public static final double INCENTIVE_VALID_FEEDBACK_RATE = 0.8; // (FEEDBACK) / (FEEDBACK + REPORT)
	public static final int INCENTIVE = 30;


	//신고내용 표출시 최소 Feedback 과 Report 비율 (REPORT/FEEDBACK)
	public static final double RATIO_REPORT_BY_FEEDBACK = 0.3; //30퍼센트


	//Feedback comment 키워드
	public static final String LIKE_COMMENT_MESSAGE = "좋아요";
	public static final String EXACT_INFO_COMMENT_MESSAGE = "정확한 정보예요";
	public static final String SYMPATHY_COMMENT_MESSAGE = "공감돼요";
	public static final String HELPFUL_COMMENT_MESSAGE = "도움이 되었어요";
	//Report comment 키워드
	public static final String UNRELIABLE_COMMENT_MESSAGE = "부정확한 정보에요";
	public static final String SUSPICIOUS_ADVERTISING_MESSAGE = "광고가 의심돼요";
	public static final String IMPROPER_MESSAGE = "부정확한 정보에요";
	public static final String EXCESSIVE_PUBLICITY_MESSAGE = "지나친 홍보에요";
	public static final String INSINCERE_WRITING_MESSAGE = "성의없는 게시물이에요";
	public static final String DUPLICATE_REVIEWS_MESSAGE = "중복 게시물이에요";
	public static final String INACCURATE_ADDRESS_MESSAGE = "주소가 잘못됐어요";

	//Feedback Result Message
	public static final String FEEDBACK_ADDED_MESSAGE = "피드백이 추가되었습니다.";
	public static final String FEEDBACK_REMOVED_MESSAGE = "피드백이 삭제되었습니다.";
	public static final String FEEDBACK_CHANGED_MESSAGE = "피드백이 변경되었습니다.";
	
	//Bookmark Result Message
	public static final String BOOKMARK_ADD_MESSAGE = "북마크가 추가 되었습니다.";
	public static final String BOOKMARK_DELETE_MESSAGE = "북마크가 제거 되었습니다.";
	


}
