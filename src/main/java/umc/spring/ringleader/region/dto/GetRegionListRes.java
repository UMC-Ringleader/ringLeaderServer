package umc.spring.ringleader.region.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionListRes {
    private int regionId;
    private String placeName;
    private int regionActivity;
}
