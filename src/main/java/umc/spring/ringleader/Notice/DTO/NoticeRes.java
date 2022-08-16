package umc.spring.ringleader.Notice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRes {
    private int managerId;
    private String title;
    private String content;
}
