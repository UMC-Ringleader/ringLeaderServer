package umc.spring.ringleader.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.review.model.dto.PostReviewReq;
import umc.spring.ringleader.review.model.dto.PostReviewRes;

@Service
public class ReviewService {

	private final ReviewDao reviewDao;

	@Autowired
	public ReviewService(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	public PostReviewRes saveReview(PostReviewReq req) {
		int savedId = reviewDao.saveReview(req);

		return new PostReviewRes(savedId, req.getTitle());
	}

	public int deleteReview(int deleteId) {
		int deleteCode = reviewDao.deleteReview(deleteId);

		return deleteCode;
	}
}
