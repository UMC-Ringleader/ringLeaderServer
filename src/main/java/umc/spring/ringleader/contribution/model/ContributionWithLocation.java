package umc.spring.ringleader.contribution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContributionWithLocation {

    public ContributionWithLocation() {
    }

    private String placeName;
    private long contribution;
}
