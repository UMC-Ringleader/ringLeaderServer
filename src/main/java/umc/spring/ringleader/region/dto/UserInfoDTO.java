package umc.spring.ringleader.region.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDTO {
    private int userId;
    private int lastVisitRegionId;
}
