package umc.spring.ringleader.review.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReviewReq {
	@ApiModelProperty(required = true)
	private int userId;
	@ApiModelProperty(required = true, example = "맛있는 음식점")
	private String reviewTitle;
	@ApiModelProperty(required = true, example = "타파스바")
	private String title;
	@ApiModelProperty(required = true, example = "음식점")
	private String category;
	@ApiModelProperty(required = true, example = "음식점")
	private int RRDId;
	@ApiModelProperty(required = true, example = "한식")
	private List<String> hashtags;

	@ApiModelProperty(required = true)
	private String contents;
	private int regionId;
	private List<PostImage> postImages;
}
