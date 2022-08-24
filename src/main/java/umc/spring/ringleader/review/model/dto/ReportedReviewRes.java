package umc.spring.ringleader.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;

import java.util.List;

@Getter
@Setter
public class ReportedReviewRes extends ReviewRes {
    private String reportedContent;

    public ReportedReviewRes(int userId, String nickName, int userContribution, int reviewId, String title, String reviewTitle,String category, String hashtag1, String hashtag2,
                             String hashtag3, String contents, List<String> imgUrls, ReviewFeedBacks reviewFeedBacks, boolean bookmarked, String reportedContent) {
        super(userId, nickName, userContribution, reviewId, title,reviewTitle, category, hashtag1, hashtag2, hashtag3, contents, imgUrls, reviewFeedBacks, bookmarked);
        this.reportedContent = reportedContent;
    }
}
