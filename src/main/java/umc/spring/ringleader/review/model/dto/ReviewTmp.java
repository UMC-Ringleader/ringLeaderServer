package umc.spring.ringleader.review.model.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTmp {
	private int reviewId;
	private String title;
	private String category;
	private String hashtag1;
	private String hashtag2;
	private String hashtag3;
	private String contents;
	private int userId;
	
	public ReviewRes toReviewRes(String userName, int userContribution, List<String> imgUrls, ReviewFeedBacks reviewFeedBacks) {
		return new ReviewRes(
			userName,
			userContribution,
			title,
			category,
			hashtag1,
			hashtag2,
			hashtag3,
			contents,
			imgUrls,
			reviewFeedBacks
		);
	}
}