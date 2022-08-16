package umc.spring.ringleader.search.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchTmp {
    private int userId;
    private String nickName;
    private int contribution;
    private int reviewId;
    private String title;
    private String category;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
    private String contents;
}