package umc.spring.ringleader.review.model.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRes {
	String userName;
	int userContribution;
	private String title;
	private String category;
	private String hashtag1;
	private String hashtag2;
	private String hashtag3;
	private String contents;
	private List<String> imgUrls;
}
