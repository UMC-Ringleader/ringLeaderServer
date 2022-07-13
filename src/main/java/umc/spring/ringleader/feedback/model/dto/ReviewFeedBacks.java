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
	private int exactInfoCount; //정확한 정보예요
	private int sympathyCnt; //공감돼요
	private int helpfulCnt; //도움이 되었어요
}
