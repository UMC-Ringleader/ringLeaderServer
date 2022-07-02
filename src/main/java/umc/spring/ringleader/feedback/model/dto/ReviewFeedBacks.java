package umc.spring.ringleader.feedback.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFeedBacks {
	private int likeCount; //좋아요
	private int exactInfoCount; //정확한 정보에요
	private int unreliable; //광고 같아요
}
