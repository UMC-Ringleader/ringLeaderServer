package umc.spring.ringleader.notice1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@AllArgsConstructor

public class PostNoticeRes {
    private int noticeId;
    private String title;
}