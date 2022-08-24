package umc.spring.ringleader.review.model.dto;

import java.util.List;

import lombok.*;
import umc.spring.ringleader.feedback.model.dto.ReviewFeedBacks;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRes {
    private int userId; //
    private String nickName; //
    private int userContribution; //
    private int reviewId; //
    private String title; //
    private String category; //
    private String hashtag1; //
    private String hashtag2; //
    private String hashtag3; //
    private String contents; //
    private List<String> imgUrls;
    private ReviewFeedBacks reviewFeedBacks;
    private boolean bookmarked;
}