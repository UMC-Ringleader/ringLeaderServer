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
public class ReviewRes {
	private int userId;
	private String nickName;
	private int userContribution;
	private int reviewId;
	private String title;
	private String reviewTitle;
	private String category;
	private String hashtag1;
	private String hashtag2;
	private String hashtag3;
	private String contents;
	private List<String> imgUrls;
	private ReviewFeedBacks reviewFeedBacks;
	private boolean bookmarked;
}
