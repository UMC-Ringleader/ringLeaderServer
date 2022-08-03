package umc.spring.ringleader.oauth;

import static umc.spring.ringleader.config.BaseResponseStatus.*;

import java.lang.reflect.InvocationTargetException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;

@Slf4j
@RequestMapping("/oauth")
@RequiredArgsConstructor
@RestController
public class OAuthController {

	private final OAuthService oAuthService;

	@PostMapping("/login/{service}")
	public ResponseEntity<BaseResponse> loginByAccessToken(
		@RequestHeader String accessToken,
		@PathVariable String service
	) throws BaseException {
		log.debug("[OAUTH LOGIN BY] : {}", service.toUpperCase());

		String userEmail = oAuthService.requestToService(accessToken, service);

		return new ResponseEntity<>(new BaseResponse<>(userEmail), HttpStatus.OK);
	}
}
