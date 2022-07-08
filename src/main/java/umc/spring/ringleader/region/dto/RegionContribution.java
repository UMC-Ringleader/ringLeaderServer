package umc.spring.ringleader.region.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionContribution {
    private int regionId;
    private int contribution;
}
