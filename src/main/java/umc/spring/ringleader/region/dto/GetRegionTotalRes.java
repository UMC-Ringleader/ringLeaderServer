package umc.spring.ringleader.region.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionTotalRes {
    @ApiModelProperty(example = "최근 방문 Region")
    GetRegionListRes lastVisitRegion;

    @ApiModelProperty(example = "나머지 Region")
    List<GetRegionListRes> listRegion;
}
