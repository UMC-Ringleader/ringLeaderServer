package umc.spring.ringleader.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@AllArgsConstructor

public class PostSignupRes {
    @ApiModelProperty(required = true)
    private int userId;
}
