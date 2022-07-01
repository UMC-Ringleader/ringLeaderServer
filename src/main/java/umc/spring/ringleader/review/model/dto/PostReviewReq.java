package umc.spring.ringleader.review.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReviewReq {
	private int userId;
	private String title;
	private String category;
	private String hashtag1;
	private String hashtag2;
	private String hashtag3;
	private String contents;
	private int regionId;
}
