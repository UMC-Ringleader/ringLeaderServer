package umc.spring.ringleader.oauth.kakao;

import lombok.extern.slf4j.Slf4j;
import umc.spring.ringleader.oauth.utils.OAuthAttributeUtil;

import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class KakaoService {
    public String getAccessTokenJsonData(String accessToken){
        String result = WebClient.builder()
            .baseUrl("https://kapi.kakao.com/v2/user/me")
            .defaultHeader("Authorization", "Bearer " + accessToken)
            .build()
            .get()
            .retrieve()
            .bodyToMono(String.class)
            .block();


        return OAuthAttributeUtil.getEmailFromAttribute(result);
    }
}
