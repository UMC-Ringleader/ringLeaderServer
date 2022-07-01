package umc.spring.ringleader.contribution1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContributionRanking {
    public ContributionRanking() {
    }

    private int ranking;
    private int userId;
    private String nickName;
    private int contribution;
}
