package umc.spring.ringleader.review.web;

import static umc.spring.ringleader.config.BaseResponseStatus.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.review.ReviewService;
import umc.spring.ringleader.review.model.dto.*;

@Slf4j
@RestController
@RequestMapping("/app/review")
public class ReviewController {

	@Autowired
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	/**
	 * Review 등록 메서드
	 *
	 * @param postReviewReq
	 * @return PostReviewRes
	 */
	@ResponseBody
	@PostMapping("/")
	public BaseResponse<PostReviewRes> addPost(@RequestBody PostReviewReq postReviewReq) {
		log.info("[Review][POST] : Review 등록 / reviewId = {}", postReviewReq.getUserId());

		/**
		 예외
		 Not Null
		 1. Title
		 2. hashTag1
		 3. contents
		 */

		// if (reviewPostReq.getTitle() == null) {
		// 	return new BaseResponse<>();
		// } else if (reviewPostReq.getHashtag1() == null) {
		// 	return new BaseResponse<>();
		// } else if (reviewPostReq.getContents() == null) {
		// 	return new BaseResponse<>();
		// }

		PostReviewRes res = reviewService.saveReview(postReviewReq);
		return new BaseResponse<>(res);
	}

	/**
	 * Review 삭제 메서드
	 *
	 * @param reviewId reviewId에 해당하는 이미지도 모두 삭제
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("/delete/{reviewId}")
	public BaseResponse<String> deletePost(@PathVariable int reviewId) {
		log.info("[Review][DELETE] : Review 삭제 / reviewId = {}", reviewId);

		try {
			int i = reviewService.deleteReview(reviewId);
			if (i == 0) {
				throw new BaseException(REVIEW_NULL);
			}

			return new BaseResponse<>(SUCCESS);
		} catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * RegionId로 리뷰 조회 (최신순으로 정렬)
	 *
	 * @param regionId
	 * @param loginUserId (Nullable)
	 * @return
	 */
	@ResponseBody
	@GetMapping("/{regionId}")
	public BaseResponse<List<ReviewRes>> getRegionReviewsLately(
			@PathVariable int regionId,
			@RequestParam(required = false) Integer loginUserId
	) {
		log.info("[Review][GET] : RegionId로 리뷰 조회 (최신순으로 정렬) / regionId = {}", regionId);
		log.info("[Review][GET] : 로그인 유저 / category = {}", loginUserId);
		//로그인한 경우 최근 방문 동네 반영
		if (loginUserId != null) {
			reviewService.updateLastVisitedRegion(loginUserId, regionId);
		}

		List<ReviewRes> reviewsByRegion = reviewService.getReviewsByRegion(regionId, loginUserId);

		return new BaseResponse<>(reviewsByRegion);
	}

	/**
	 * category필터 추가하여 리뷰 조회
	 *
	 * @param category
	 * @param regionId
	 * @param loginUserId (Nullable, Login하지 않은 회원에 대해서)
	 * @return
	 */
	@ResponseBody
	@GetMapping("/{regionId}/category")
	public BaseResponse<List<ReviewRes>> getRegionReviewsByCategory(
			@RequestParam String category,
			@PathVariable Integer regionId,
			@RequestParam(required = false) Integer loginUserId
	) {
		log.info("[Review][GET] : category필터 추가하여 리뷰 조회 / category = {} ,regionId = {}", category, regionId);
		log.info("[Review][GET] : 로그인 유저 / category = {}", loginUserId);

		List<ReviewRes> reviewsByCategory = reviewService.getReviewsByCategory(category, regionId, loginUserId);

		return new BaseResponse<>(reviewsByCategory);
	}

	/**
	 * 해당 User의 지역별 Review조회
	 *
	 * @param userId
	 * @param regionId
	 * @param loginUserId (Nullable, Login하지 않은 회원에 대해서)
	 * @return
	 */
	@ResponseBody
	@GetMapping("/profile/{userId}")
	public BaseResponse<List<ReviewRes>> getRegionReviewsByCategory(
			@PathVariable int userId,
			@RequestParam Integer regionId,
			@RequestParam(required = false) Integer loginUserId
	) {
		log.info("[Review][GET] : 해당 User의 지역별 Review조회 / userId = {} ,regionId = {}", userId, regionId);
		log.info("[Review][GET] : 로그인 유저 / category = {}", loginUserId);

		List<ReviewRes> usersReviewByRegion = reviewService.getUsersReviewByRegion(userId, regionId, loginUserId);

		return new BaseResponse<>(usersReviewByRegion);
	}

	/**
	 * UserId와 ReviewId를 받아 북마크 추가/삭제
	 *
	 * @param req
	 * @return
	 */
	@ResponseBody
	@PostMapping("/bookmark")
	public BaseResponse<String> updateReviewToBookmark(@RequestBody PostReviewBookmark req) {
		log.info(
				"[Review][POST] : 해당 User가 해당 Review에 북마크 버튼 클릭 / userId = {}, reviewId = {}",
				req.getUserId(), req.getReviewId()
		);

		String updateResultMessage = reviewService.updateReviewToBookmark(req);
		return new BaseResponse<>(updateResultMessage);
	}

	@GetMapping("/details/{reviewId}/")
	public BaseResponse<ReviewRes> gerReviewByReviewId(@PathVariable int reviewId,@RequestParam(required = false) Integer loginUserId) {
		log.info("[Review][GET] : 해당 User의 지역별 Review조회 / userId = {}, reviewId = {} ", loginUserId, reviewId);
		ReviewRes reviewByReviewId = reviewService.getReviewByReviewId(loginUserId, reviewId);
		return new BaseResponse<>(reviewByReviewId);
	}
}
