package umc.spring.ringleader.oauth.naver;

import static umc.spring.ringleader.config.BaseResponseStatus.*;
import static umc.spring.ringleader.config.Constant.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.Constant;
import umc.spring.ringleader.oauth.utils.OAuthAttributeUtil;

@Slf4j
@Service
public class NaverOAuthService {

	public String requestToNaver(String accessToken) {
		String result = WebClient.builder()
			.baseUrl("https://openapi.naver.com/v1/nid/me")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.get()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.debug("[USER INFO FROM NAVER] : {}", result);
		return OAuthAttributeUtil.getEmailFromAttribute(result);
	}

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

		String result = WebClient.builder()
			.baseUrl(requestURL)
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.get()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.debug("[USER INFO FROM NAVER] : {}", result);
		return OAuthAttributeUtil.getEmailFromAttribute(result);
	}

}
