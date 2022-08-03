package umc.spring.ringleader.oauth.google;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import umc.spring.ringleader.config.BaseResponse;

@RequiredArgsConstructor
@RestController
public class GoogleLoginController {

    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/login")
    public BaseResponse<String> loginNaver(@RequestHeader String accessToken) {
        // String userEmail = googleOAuthService.requestToNaver(accessToken);
        return new BaseResponse<>("userEmail");
    }

}
