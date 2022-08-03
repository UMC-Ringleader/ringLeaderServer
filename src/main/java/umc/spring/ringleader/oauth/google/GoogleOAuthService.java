package umc.spring.ringleader.oauth.google;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleOAuthService {

	public ResponseEntity<String> requestProfile(HttpEntity request) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(
			"https://oauth2.googleapis.com/tokeninfo",
			HttpMethod.POST,
			request,
			String.class
		);
	}

	public HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		return new HttpEntity<>(headers);
	}

}
