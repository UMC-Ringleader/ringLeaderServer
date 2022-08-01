package umc.spring.ringleader.oauth.naver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

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
		return getEmailFromAttribute(result);
	}


	private String getEmailFromAttribute(String attribute) {
		Pattern p = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
		Matcher m = p.matcher(attribute);

		while (m.find()) {
			if (m.group(1) != null) {
				break;
			}
		}

		return m.group(1);
	}
}
