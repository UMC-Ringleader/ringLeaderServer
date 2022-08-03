package umc.spring.ringleader.oauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.oauth.google.GoogleOAuthService;
import umc.spring.ringleader.oauth.naver.NaverOAuthService;

@Slf4j
@RequestMapping("/oauth")
@RequiredArgsConstructor
@RestController
public class OAuthController {

	private final NaverOAuthService naverOAuthService;

	@PostMapping("/login/{service}")
	public ResponseEntity<BaseResponse> loginByAccessToken(
		@RequestHeader String accessToken,
		@PathVariable String service
	) {
		log.debug("[OAUTH LOGIN BY] : {}", service.toUpperCase());
		String userEmail = "";
		try {
			userEmail = naverOAuthService.requestToService(accessToken, service);
		} catch (BaseException e) {
			return new ResponseEntity<>(new BaseResponse<>(e.getStatus()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
