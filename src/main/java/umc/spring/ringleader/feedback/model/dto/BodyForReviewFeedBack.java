package umc.spring.ringleader.feedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class BodyForReviewFeedBack {
    public BodyForReviewFeedBack() {

    }

    private int userId;
    private int reviewId;
    private String comment;
}
