package umc.spring.ringleader.notice1.DTO;
import lombok.AllArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostNoticeReq {
    @ApiModelProperty(required = true)
    private int managerId;
    @ApiModelProperty(required = true)
    private String title;
    @ApiModelProperty(required = true)
    private String content;

    //기본 생성자 추가
    public PostNoticeReq() {}

    public PostNoticeReq(int id, String username, String password, String email) {
        this.managerId = managerId;
        this.title = title;
        this.content = content;
    }
}
