package umc.spring.ringleader.contribution1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContributionWithNickNameByReviewId {

    public ContributionWithNickNameByReviewId() {
    }

    private String nickName;
    private int contribution;

}
