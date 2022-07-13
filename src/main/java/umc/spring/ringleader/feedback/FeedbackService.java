package umc.spring.ringleader.feedback;

import static umc.spring.ringleader.config.Constant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.config.Constant;
import umc.spring.ringleader.contribution1.ContributionService;
import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;
import umc.spring.ringleader.review.ReviewService;

@Service
public class FeedbackService {

	private FeedbackDao feedbackDao;
	private ContributionService contributionService;

	@Autowired
	public FeedbackService(FeedbackDao feedbackDao, ContributionService contributionService) {
		this.feedbackDao = feedbackDao;
		this.contributionService = contributionService;
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

		//"좋아요" 개수
		int likeCnt = feedbackDao.getReviewFeedBackByComment(
			reviewId, LIKE_COMMENT_MESSAGE
		);

		//"정확한 정보예요" 개수
		int exactInfoCnt = feedbackDao.getReviewFeedBackByComment(
			reviewId, EXACT_INFO_COMMENT_MESSAGE
		);

		//"공감돼요" 개수
		int sympathyCnt = feedbackDao.getReviewFeedBackByComment(
			reviewId, SYMPATHY_COMMENT_MESSAGE
		);

		//"도움이 되었어요" 개수
		int helpfulCnt = feedbackDao.getReviewFeedBackByComment(
			reviewId, HELPFUL_COMMENT_MESSAGE
		);

		return new ReviewFeedBacks(likeCnt, exactInfoCnt, sympathyCnt, helpfulCnt);
	}

	public String createOrDeleteReviewFeedback(int userId, int reviewId, String comment) {
		int regionId = feedbackDao.getRegionIdByReviewId(reviewId);
		int checking = feedbackDao.existingVerification(userId, reviewId);

		if (checking == NULL_DATA_CODE) {
			//존재하지 않는경우 -> 추가
			feedbackDao.createFeedback(userId, reviewId, comment);
			contributionService.contributionUpdate(userId, regionId, ADD_FEEDBACK); //+1점

			return FEEDBACK_ADDED_MESSAGE;
		}
		else if(feedbackDao.getComment(userId,reviewId).equals(comment)) {
			//존재하는 경우 -> 기존과 동일한 경우 -> 삭제
			feedbackDao.deleteFeedback(userId, reviewId);
			contributionService.contributionUpdate(userId, regionId, DELETE_FEEDBACK); //-1점
			return FEEDBACK_REMOVED_MESSAGE;
		}
		else{
			//존재하는 경우 -> 기존과 다른 경우 -> 변경
			feedbackDao.deleteFeedback(userId, reviewId);
			feedbackDao.createFeedback(userId, reviewId,comment);
			return FEEDBACK_CHANGED_MESSAGE;
		}
	}

}
