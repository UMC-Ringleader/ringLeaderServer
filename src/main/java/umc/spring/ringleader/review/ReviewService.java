package umc.spring.ringleader.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.contribution1.ContributionService;
import umc.spring.ringleader.contribution1.model.ContributionWithNickNameByReviewId;
import umc.spring.ringleader.feedback.FeedbackService;
import umc.spring.ringleader.review.model.dto.*;

@Service
public class ReviewService {

	private final ReviewDao reviewDao;
	private final ContributionService contributionService;
	private final FeedbackService feedbackService;

	@Autowired
	public ReviewService(ReviewDao reviewDao, ContributionService contributionService, FeedbackService feedbackService) {
		this.reviewDao = reviewDao;
		this.contributionService = contributionService;
		this.feedbackService = feedbackService;
	}

	public PostReviewRes saveReview(PostReviewReq req) {
		int savedId = reviewDao.saveReview(req);

		//Image가 없는 경우 기여도+3
		if (req.getPostImages()==null) {
			contributionService.contributionUpdate(req.getUserId(),req.getRegionId(),3);
		}

		else{
			//Image가 있는 경우 기여도+5
			//ReviewImage Table에 저장
			contributionService.contributionUpdate(req.getUserId(),req.getRegionId(),5);
			for (PostImage p : req.getPostImages()) {

				reviewDao.insertImages(savedId, p);
			}
		}
		return new PostReviewRes(savedId, req.getTitle());


	}

	public int deleteReview(int deleteId) {
		// 삭제할 리뷰의 점수조작을 위한 간단한 정보 받아옴 (userId, regionId)
		ReviewSearchTemp tempReview = reviewDao.getUserRegionIdByReviewId(deleteId);
		// 이미지 있으면
		if (reviewDao.isImageExist(deleteId)) {
			// 점수 차감 (-5)
			contributionService.contributionUpdate(tempReview.getUserId(),tempReview.getRegionId(),-5);
			reviewDao.deleteReviewImages(deleteId);
		}
		// 이미지 없으면
		else {
			contributionService.contributionUpdate(tempReview.getUserId(),tempReview.getRegionId(),-3);
		}
		//ReviewId로 Image삭제, Review 삭제
		int deleteCode = reviewDao.deleteReview(deleteId);

		return deleteCode;
	}

	public void updateLastVisitedRegion(int userId, int regionId) {
		reviewDao.updateLastVisitedRegion(userId, regionId);
	}

	public List<ReviewRes> getReviewsByRegion(int regionId) {
		List<ReviewTmp> reviewTmps = reviewDao.getReviewsByRegion(regionId);
		return toReviewRes(reviewTmps);
	}

	public List<ReviewRes> getReviewsByCategory(String category, int regionId) {
		List<ReviewTmp> reviewsByCategory = reviewDao.getReviewsByCategory(category, regionId);
		return toReviewRes(reviewsByCategory);
	}

	public List<ReviewRes> getUsersReviewByRegion(int userId, int regionId) {
		List<ReviewTmp> userReviewsByRegion = reviewDao.getUserReviewsByRegionId(userId, regionId);
		return toReviewRes(userReviewsByRegion);
	}

	/*
		ReviewDao에선 ReviewTmp 타입으로 응답
		최종 반환 결과인 ReviewRes로 변환하는 메서드
		ReviewRes로는 ReviewTmp에서
		리뷰 작성자의 닉네임, 지역 기여도, reviewImage가 추가됨 + 피드백 정보
	*/
	private List<ReviewRes> toReviewRes(List<ReviewTmp> reviewTmps) {
		List<ReviewRes> reviewResList = new ArrayList<>();
		for (ReviewTmp reviewTmp : reviewTmps) {
			ContributionWithNickNameByReviewId contributionNickname =
				contributionService.ContributionWithNickNameByReviewId(reviewTmp.getReviewId());

			List<String> reviewImgs = reviewDao.getReviewImgs(reviewTmp.getReviewId());

			reviewResList.add(
				reviewTmp.toReviewRes(
					contributionNickname.getNickName(),
					contributionNickname.getContribution(),
					reviewImgs,
					feedbackService.getFeedbacksByReviewId(reviewTmp.getReviewId())
				)
			);
		}

		return reviewResList;
	}



}
