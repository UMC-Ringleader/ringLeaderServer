package umc.spring.ringleader.region.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRegionRes {
    private int regionId;
    private String placeName;
    private String image;
    private String location;
    private String regionInfo;
}
