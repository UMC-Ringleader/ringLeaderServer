package umc.spring.ringleader.oauth.naver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import umc.spring.ringleader.config.BaseResponse;

@RequiredArgsConstructor
@RequestMapping("/naver")
@RestController
public class NaverController {

	private final NaverOAuthService naverOAuthService;

	@PostMapping("/login")
	public BaseResponse<String> loginNaver(@RequestHeader String accessToken) {
		String userEmail = naverOAuthService.requestToNaver(accessToken);
		return new BaseResponse<>(userEmail);
	}

}
