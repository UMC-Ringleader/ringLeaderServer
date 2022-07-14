package umc.spring.ringleader.review.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewIncentiveTemp {
    int reviewId;
    int userId;
    int regionId;
}
