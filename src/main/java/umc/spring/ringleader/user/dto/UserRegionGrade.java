package umc.spring.ringleader.user.dto;

import lombok.*;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.contribution.model.Grade;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegionGrade {

    private String placeName;
    private long contribution;
    private Grade grade;

    public UserRegionGrade(ContributionWithLocation contributionWithLocation, Grade grade) {
        this.placeName = contributionWithLocation.getPlaceName();
        this.contribution = contributionWithLocation.getContribution();
        this.grade = grade;
    }
}
