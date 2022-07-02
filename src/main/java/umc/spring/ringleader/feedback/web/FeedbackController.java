package umc.spring.ringleader.feedback.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.feedback.FeedbackService;
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

}
