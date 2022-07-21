package umc.spring.ringleader.region.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionListRes {
    @ApiModelProperty(example = "Region ID")
    private int regionId;

    @ApiModelProperty(example = "Region 이름")
    private String placeName;

    @ApiModelProperty(example = "Region 활성도")
    private int regionActivity;
}
