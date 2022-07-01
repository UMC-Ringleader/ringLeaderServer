package umc.spring.ringleader.review.web;

import static umc.spring.ringleader.config.BaseResponseStatus.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.config.BaseResponseStatus;
import umc.spring.ringleader.review.ReviewService;
import umc.spring.ringleader.review.model.dto.PostReviewReq;
import umc.spring.ringleader.review.model.dto.PostReviewRes;

@Slf4j
@RestController
@RequestMapping("/app/review")
public class ReviewController {

	@Autowired
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@ResponseBody
	@PostMapping("/")
	public BaseResponse<PostReviewRes> addPost(@RequestBody PostReviewReq postReviewReq) {
		log.debug("Reviewer Id : {}", postReviewReq.getUserId());
		log.debug("Review Region : {}", postReviewReq.getRegionId());
		log.debug("Review Title : {}", postReviewReq.getTitle());

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

	@ResponseBody
	@DeleteMapping("/delete/{reviewId}")
	public BaseResponse<String> deletePost(@PathVariable int reviewId) {
		log.debug("Delete Review Id : {}", reviewId);

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

}
