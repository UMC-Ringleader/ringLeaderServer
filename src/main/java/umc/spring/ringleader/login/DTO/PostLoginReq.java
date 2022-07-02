package umc.spring.ringleader.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostLoginReq {
    private String email;
    private String pwd;
}
