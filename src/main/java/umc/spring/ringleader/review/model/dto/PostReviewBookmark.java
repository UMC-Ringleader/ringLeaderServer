package umc.spring.ringleader.review.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReviewBookmark {
	@ApiModelProperty(required = true)
	private int userId;
	@ApiModelProperty(required = true)
	private int reviewId;
}
