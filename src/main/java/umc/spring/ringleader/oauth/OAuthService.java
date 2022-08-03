package umc.spring.ringleader.oauth;

import static umc.spring.ringleader.config.BaseResponseStatus.*;
import static umc.spring.ringleader.config.Constant.*;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.oauth.utils.OAuthAttributeUtil;

@Slf4j
@Service
public class OAuthService {

	public String requestToService(String accessToken, String service) throws BaseException {
		String requestURL = null;
		if (service.equals("kakao")) {
			requestURL = KAKAO_GET_USERINFO_URL;
		} else if (service.equals("naver")) {
			requestURL = NAVER_GET_USERINFO_URL;
		} else if (service.equals("google")) {
			requestURL = GOOGLE_GET_USERINFO_URL;
		}
		if (requestURL == null) {
			throw new BaseException(NOT_PROVIDE_LOGIN_SERVICE);
		}

		log.debug("[REQUEST URL] : {}", requestURL);


		String result = WebClient.builder()
			.baseUrl("https://kapi.kakao.com/v2/user/me")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.post()
			.retrieve()
			.bodyToMono(String.class)
			.block();


		log.debug("[USER INFO FROM {}] : {}", service, result);
		return OAuthAttributeUtil.getEmailFromAttribute(result);
	}

}
