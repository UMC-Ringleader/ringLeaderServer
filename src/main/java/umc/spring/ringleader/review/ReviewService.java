package umc.spring.ringleader.review;

import static umc.spring.ringleader.config.Constant.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import umc.spring.ringleader.contribution1.ContributionService;
import umc.spring.ringleader.feedback.FeedbackService;
import umc.spring.ringleader.report.ReportRepository;
import umc.spring.ringleader.report.model.CheckingNonconformity;
import umc.spring.ringleader.review.model.dto.*;

@Slf4j
@Service
public class ReviewService {

	private final ReviewDao reviewDao;
	private final ContributionService contributionService;
	private final FeedbackService feedbackService;
	private final ReportRepository reportRepository;

	@Autowired
	public ReviewService(ReviewDao reviewDao, ContributionService contributionService,
						 FeedbackService feedbackService, ReportRepository reportRepository) {
		this.reviewDao = reviewDao;
		this.contributionService = contributionService;
		this.feedbackService = feedbackService;
		this.reportRepository = reportRepository;
	}

	public PostReviewRes saveReview(PostReviewReq req) {
		int savedId = reviewDao.saveReview(req);

		//Image가 없는 경우 기여도 + 5
		if (req.getPostImages() == null) {
			contributionService.contributionUpdate(
					req.getUserId(),
					req.getRegionId(),
					POST_NOT_IMG_REVIEW
			);
		} else {
			//Image가 있는 경우 기여도 +10
			//ReviewImage Table에 저장
			contributionService.contributionUpdate(
					req.getUserId(),
					req.getRegionId(),
					POST_IMG_REVIEW
			);

			for (PostImage p : req.getPostImages()) {
				reviewDao.insertImages(savedId, p);
			}
		}
		return new PostReviewRes(savedId, req.getTitle());

	}

	// 매일 00시에 4일전 리뷰에 대한 인센티브 여부 확인 및 점수 부여
	@Scheduled(cron = "0 0 0 * * *")
	public void checkIncentive(){
		// 4일 이전 리뷰
		log.info("인센티브 메서드 실행");
		List<ReviewIncentiveTemp> reviewListToCheckIncentive = getReviewListToCheckIncentive();
		for (ReviewIncentiveTemp reviewTemp : reviewListToCheckIncentive) {
			if (isValidIncentive(reviewTemp.getReviewId())) { // 피드백 10+ && 80퍼 이상
				contributionService.contributionUpdate(
						reviewTemp.getUserId(),
						reviewTemp.getRegionId(),
						INCENTIVE
				);
			}
		}
	}


	// 인센티브 부여 조건 충족 판단 메서드
	private boolean isValidIncentive(int reviewId){
		CheckingNonconformity reportedAndFeedbackCount = reportRepository.getReportedAndFeedbackCount(reviewId);
		int sum = reportedAndFeedbackCount.getFeedbackCount() + reportedAndFeedbackCount.getReportedCount();
		double rate = reportedAndFeedbackCount.getFeedbackCount()/sum;
		if (sum >= INCENTIVE_VALID_FEEDBACK_COUNT && rate >= INCENTIVE_VALID_FEEDBACK_RATE) {
			return true;
		}
		else {
			return false;
		}
	}

	public int deleteReview(int deleteId) {
		// 삭제할 리뷰의 점수조작을 위한 간단한 정보 받아옴 (userId, regionId)
		ReviewSearchTemp tempReview = reviewDao.getUserRegionIdByReviewId(deleteId);
		// 이미지 있으면
		if (reviewDao.isImageExist(deleteId)) {
			// 점수 차감 (-10)
			contributionService.contributionUpdate(
				tempReview.getUserId(),
				tempReview.getRegionId(),
				DELETE_IMG_REVIEW);
			reviewDao.deleteReviewImages(deleteId);
		}
		// 이미지 없으면 (-5)
		else {
			contributionService.contributionUpdate(
				tempReview.getUserId(),
				tempReview.getRegionId(),
				DELETE_NOT_IMG_REVIEW);
		}
		//ReviewId로 Image삭제, Review 삭제
		int deleteCode = reviewDao.deleteReview(deleteId);

		return deleteCode;
	}

	public void updateLastVisitedRegion(int userId, int regionId) {
		reviewDao.updateLastVisitedRegion(userId, regionId);
		//로그인시 첫로그인인지 아닌지 구분하고 기여도 조작
		contributionService.firstLogin(userId, regionId);
	}

	public List<ReviewRes> getReviewsByRegion(int regionId, Integer loginUserId) {
		List<ReviewTmp> reviewTmps = reviewDao.getReviewsByRegion(regionId);
		return toReviewRes(reviewTmps, loginUserId);
	}

	public List<ReviewRes> getReviewsByCategory(String category, int regionId, Integer loginUserId) {
		List<ReviewTmp> reviewsByCategory = reviewDao.getReviewsByCategory(category, regionId);
		return toReviewRes(reviewsByCategory, loginUserId);
	}

	public List<ReviewRes> getUsersReviewByRegion(int userId, int regionId, Integer loginUserId) {
		List<ReviewTmp> userReviewsByRegion = reviewDao.getUserReviewsByRegionId(userId, regionId);
		return toReviewRes(userReviewsByRegion, loginUserId);
	}

	public String updateReviewToBookmark(PostReviewBookmark req) {
		int checkingCode = reviewDao.existingBookmark(req.getUserId(), req.getReviewId());

		if (checkingCode == NULL_DATA_CODE) {
			//북마크 한 적 없는 리뷰인 경우 -> 추가
			reviewDao.createReviewBookmark(req.getUserId(), req.getReviewId());
			return BOOKMARK_ADD_MESSAGE;
		} else {
			//북마크가 이미 되어 있는 경우 -> 제거
			reviewDao.deleteReviewBookmark(req.getUserId(), req.getReviewId());
			return BOOKMARK_DELETE_MESSAGE;
		}
	}

	/*
		ReviewDao에선 ReviewTmp 타입으로 응답
		최종 반환 결과인 ReviewRes로 변환하는 메서드
		ReviewRes로는 ReviewTmp에서
		리뷰 작성자의 닉네임, 지역 기여도, reviewImage가 추가됨 + 피드백 정보
	*/
	private List<ReviewRes> toReviewRes(List<ReviewTmp> reviewTmps, Integer loginUserId) {
		List<ReviewRes> reviewResList = new ArrayList<>();

		//로그인 하지 않은 사용자인 경우
		//북마크 정보는 모두 False
		if (loginUserId == null) {
			for (ReviewTmp reviewTmp : reviewTmps) {
				List<String> reviewImgs = reviewDao.getReviewImgs(reviewTmp.getReviewId());
				reviewResList.add(
					reviewTmp.toReviewRes(
						reviewImgs,
						feedbackService.getFeedbacksByReviewId(reviewTmp.getReviewId()),
						false
					)
				);
			}
		}
		//로그인 한 회원인 경우
		//북마크 정보를 반영해서 반환.
		else {
			for (ReviewTmp reviewTmp : reviewTmps) {
				List<String> reviewImgs = reviewDao.getReviewImgs(reviewTmp.getReviewId());

				boolean isBookmarked =
					reviewDao.existingBookmark(loginUserId, reviewTmp.getReviewId()) == 1; //1인경우 북마크된 리뷰
				reviewResList.add(
					reviewTmp.toReviewRes(
						reviewImgs,
						feedbackService.getFeedbacksByReviewId(reviewTmp.getReviewId()),
						isBookmarked
					)
				);
			}
		}

		return reviewResList;
	}


	// 인센티브 기여도 판단 대상 review 추출
	private List<ReviewIncentiveTemp> getReviewListToCheckIncentive() {
		LocalDate today = LocalDate.now();
		LocalDate editDate = today.minusDays(INCENTIVE_DATE_FLAG);
		LocalDateTime editDateStart = editDate.atTime(0, 0, 0);
		LocalDateTime editDateEnd = editDate.atTime(23, 59, 59);
		return reviewDao.getReviewListToCheckIncentive(editDateStart, editDateEnd);
	}
}
