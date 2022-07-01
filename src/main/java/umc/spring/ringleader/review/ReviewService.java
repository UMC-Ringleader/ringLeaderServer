package umc.spring.ringleader.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.contribution1.ContributionService;
import umc.spring.ringleader.review.model.dto.*;

@Service
public class ReviewService {

	private final ReviewDao reviewDao;
	private final ContributionService contributionService;

	@Autowired
	public ReviewService(ReviewDao reviewDao, ContributionService contributionService) {
		this.reviewDao = reviewDao;
		this.contributionService = contributionService;
	}

	public PostReviewRes saveReview(PostReviewReq req) {
		int savedId = reviewDao.saveReview(req);

		if (req.getPostImages()==null) {
			contributionService.contributionRaiseByPostReview(req.getUserId(),req.getRegionId(),3);
		}

		else{

			contributionService.contributionRaiseByPostReview(req.getUserId(),req.getRegionId(),5);
			for (PostImage p : req.getPostImages()) {

				reviewDao.insertImages(savedId, p);
			}
		}
		return new PostReviewRes(savedId, req.getTitle());


	}

	public int deleteReview(int deleteId) {
		int deleteCode = reviewDao.deleteReview(deleteId);

		return deleteCode;
	}

	public void updateLastVisitedRegion(int userId, int regionId) {
		reviewDao.updateLastVisitedRegion(userId, regionId);
	}

	public List<ReviewRes> getReviewsByRegion(int regionId) {
		List<ReviewTmp> reviewTmps = reviewDao.getReviewsByRegion(regionId);
		List<ReviewRes> reviewResList = new ArrayList<>();
		for (ReviewTmp reviewTmp : reviewTmps) {
			String userName = reviewDao.getUserName(reviewTmp.getUserId());
			int contribution = reviewDao.getContributionByRegion(reviewTmp.getUserId(), regionId);
			List<String> reviewImgs = reviewDao.getReviewImgs(reviewTmp.getReviewId());

			reviewResList.add(reviewTmp.toReviewRes(userName, contribution, reviewImgs));
		}

		return reviewResList;
	}

}
