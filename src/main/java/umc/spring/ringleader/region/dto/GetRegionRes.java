package umc.spring.ringleader.region.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionRes {
    @ApiModelProperty(example = "Region ID")
    private int regionId;

    @ApiModelProperty(example = "Region 활성도")
    private int regionActivity;

    @ApiModelProperty(example = "Region 이름")
    private String placeName;

    @ApiModelProperty(example = "Region 이미지")
    private String image;

    @ApiModelProperty(example = "Region 위치정보")
    private String location;

    @ApiModelProperty(example = "Region 상세정보")
    private String regionInfo;
}
