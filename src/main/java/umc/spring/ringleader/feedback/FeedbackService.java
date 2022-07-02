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
}
