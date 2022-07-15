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
	private int userId;
	private String nickName;
	private int userContribution;
	private int reviewId;
	private String title;
	private String category;
	private String hashtag1;
	private String hashtag2;
	private String hashtag3;
	private String contents;
	public ReviewRes toReviewRes(List<String> imgUrls, ReviewFeedBacks reviewFeedBacks, boolean bookmarked) {
		return new ReviewRes(
			userId,
			nickName,
			userContribution,
			reviewId,
			title,
			category,
			hashtag1,
			hashtag2,
			hashtag3,
			contents,
			imgUrls,
			reviewFeedBacks,
			bookmarked
		);
	}

	public ReviewRes toReviewRes(List<String> imgUrls, ReviewFeedBacks reviewFeedBacks, boolean bookmarked,String reportedContent) {
		return new ReportedReviewRes(
				userId,
				nickName,
				userContribution,
				reviewId,
				title,
				category,
				hashtag1,
				hashtag2,
				hashtag3,
				contents,
				imgUrls,
				reviewFeedBacks,
				bookmarked,
				reportedContent
		);
	}

}