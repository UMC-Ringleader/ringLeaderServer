package umc.spring.ringleader.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;

@Service
public class FeedbackService {

	private FeedbackDao feedbackDao;

	@Autowired
	public FeedbackService(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	/*
		reviewId로 피드백 개수 조회
		Ex) getFeedbacksByReviewId(1)
			=> ReviewFeedBacks
			{
				likeCount = 2;
				exactInfoCount = 2;
				unreliable = 1;
			}
	 */
	public ReviewFeedBacks getFeedbacksByReviewId(int reviewId) {
		String likeComment = "좋아요";
		String exactInfoComment = "정확한 정보에요";
		String unreliableComment = "광고같아요";

		int likeCnt = feedbackDao.getReviewFeedBackByComment(reviewId, likeComment);
		int exactInfoCnt = feedbackDao.getReviewFeedBackByComment(reviewId, exactInfoComment);
		int unreliableCnt = feedbackDao.getReviewFeedBackByComment(reviewId, unreliableComment);

		return new ReviewFeedBacks(likeCnt, exactInfoCnt, unreliableCnt);
	}

	public String createOrDeleteReviewFeedback(int userId, int reviewId,String comment) {

		int checking = feedbackDao.existingVerification(userId, reviewId);

		if (checking == 0) {
			//존재하지 않는경우 -> 추가
			feedbackDao.createFeedback(userId, reviewId,comment);
			return "피드백이 추가되었습니다.";
		}
		else if(feedbackDao.getComment(userId,reviewId).equals(comment)) {
			//존재하는 경우 -> 기존과 동일한 경우 -> 삭제
			feedbackDao.deleteFeedback(userId, reviewId);
			return "피드백이 삭제되었습니다.";
		}
		else{
			//존재하는 경우 -> 기존과 다른 경우 -> 변경
			feedbackDao.deleteFeedback(userId, reviewId);
			feedbackDao.createFeedback(userId, reviewId,comment);
			return "피드백이 변경되었습니다.";
		}
	}

}
