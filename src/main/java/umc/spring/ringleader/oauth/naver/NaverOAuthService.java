package umc.spring.ringleader.oauth.naver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
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
}
