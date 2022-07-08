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







	//Feedback comment 키워드
	public static final String LIKE_COMMENT_MESSAGE = "좋아요";
	public static final String EXACT_INFO_COMMENT_MESSAGE = "정확한 정보에요";
	public static final String UNRELIABLE_COMMENT_MESSAGE = "광고같아요";
	public static final String UNLIKE_COMMENT_MESSAGE = "싫어요";

	//Feedback Result Message
	public static final String FEEDBACK_ADDED_MESSAGE = "피드백이 추가되었습니다.";
	public static final String FEEDBACK_REMOVED_MESSAGE = "피드백이 삭제되었습니다.";
	public static final String FEEDBACK_CHANGED_MESSAGE = "피드백이 변경되었습니다.";
	
	//Bookmark Result Message
	public static final String BOOKMARK_ADD_MESSAGE = "북마크가 추가 되었습니다.";
	public static final String BOOKMARK_DELETE_MESSAGE = "북마크가 제거 되었습니다.";
	


}
