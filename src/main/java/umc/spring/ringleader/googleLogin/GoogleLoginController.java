package umc.spring.ringleader.googleLogin;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class GoogleLoginController {

    private final String authUrl = "https://oauth2.googleapis.com/token";
    private final String loginUrl = "https://accounts.google.com";
    private final String redirectUrl = "http://localhost:9100/auth";
    private final String CLIENT_ID = "849641971038-nhhuhjsp9pv601raiaj9t53tmoufe7n2.apps.googleusercontent.com";
    private final String CLIENT_SECRET = "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";
    private final String scope = "profile%20email";

    @GetMapping("/auth")
    public String authGoogle(@RequestParam String code) throws JSONException {
        String accessToken = extractAccessToken(requestAccessToken(generateAuthCodeRequest(code)).getBody());
        return requestProfile(generateProfileRequest(accessToken)).getBody();
    }

//    private HttpEntity<MultiValueMap<String, String>> generateInitialUrl() {
//        HttpHeaders headers = new HttpHeaders();
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", CLIENT_ID);
//        params.add("redirect_uri", redirectUrl);
//        params.add("response_type", "code");
//        params.add("scope", scope);
//
//        return new HttpEntity<>(params, headers);
//    }

    private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        params.add("redirect_uri", redirectUrl);
        return new HttpEntity<>(params,headers);
    }

    private ResponseEntity<String> requestAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private String extractAccessToken(String accessTokenResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(accessTokenResponse);
        return jsonObject.getString("access_token");
    }

    private ResponseEntity<String> requestProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://oauth2.googleapis.com/tokeninfo",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

}
