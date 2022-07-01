package umc.spring.ringleader.region.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionTotalRes {
    GetRegionListRes lastVisitRegion;
    List<GetRegionListRes> listRegion;
}
