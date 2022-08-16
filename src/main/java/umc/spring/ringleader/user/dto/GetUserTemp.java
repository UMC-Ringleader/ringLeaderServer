package umc.spring.ringleader.user.dto;

import lombok.*;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.contribution.model.Grade;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserTemp {
    private int userId;
    private String nickName;
    private String image;
}
