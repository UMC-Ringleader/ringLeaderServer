package umc.spring.ringleader.feedback.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.feedback.FeedbackService;
import umc.spring.ringleader.feedback.model.dto.BodyForReviewFeedBack;
import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;

@Slf4j
@RestController
@RequestMapping("/app/feedback")
public class FeedbackController {

	private final FeedbackService feedbackService;

	@Autowired
	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@ResponseBody
	@GetMapping("{reviewId}")
	public BaseResponse<ReviewFeedBacks> getFeedbacksByReviewId(@PathVariable int reviewId) {
		ReviewFeedBacks feedbacks = feedbackService.getFeedbacksByReviewId(reviewId);
		return new BaseResponse<>(feedbacks);
	}

	//요청바디를 담기 위해서 형식적으로 바디를 생성해 주었으며 userId, reviewId, comment 를 담고있다.
	@PostMapping("")
	public BaseResponse<String> checkFeedback(@RequestBody BodyForReviewFeedBack bfrf) {
		String result = feedbackService.createOrDeleteReviewFeedback(bfrf.getUserId(), bfrf.getReviewId(), bfrf.getComment());

		return new BaseResponse<>(result);
	}
}
