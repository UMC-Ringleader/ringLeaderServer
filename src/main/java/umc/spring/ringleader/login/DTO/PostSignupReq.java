package umc.spring.ringleader.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostSignupReq {
    private String email;
    private String pwd;
    private String re_pwd;
    private String nickname;
}
