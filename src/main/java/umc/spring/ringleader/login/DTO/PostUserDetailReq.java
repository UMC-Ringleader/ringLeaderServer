package umc.spring.ringleader.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserDetailReq {
	private String nickname;
	private String imgUrl;
}
