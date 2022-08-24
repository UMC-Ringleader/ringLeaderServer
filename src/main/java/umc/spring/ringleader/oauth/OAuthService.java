package umc.spring.ringleader.oauth;

import static umc.spring.ringleader.config.BaseResponseStatus.*;
import static umc.spring.ringleader.config.Constant.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.jwt.JwtService;
import umc.spring.ringleader.login.LoginDao;
import umc.spring.ringleader.oauth.utils.OAuthAttributeUtil;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

	private final JwtService jwtService;

	private final LoginDao loginDao;

	public String requestToService(String accessToken, String service) throws BaseException, UnsatisfiedLinkError {
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
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + accessToken);

		HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

		String result = restTemplate.postForEntity(requestURL, httpEntity, String.class).getBody();

		log.debug("[USER INFO FROM {}] : {}", service, result);
		return OAuthAttributeUtil.getEmailFromAttribute(result);
	}

	public String getJwtByEmail(String userEmail) throws BaseException {
		try {
			Integer userId = loginDao.getUserIdByEmail(userEmail);
			return jwtService.createMemberAccessToken(userId, userEmail);
		} catch (Exception e){
			throw new BaseException(NO_SUCH_USER);
		}
	}

}
