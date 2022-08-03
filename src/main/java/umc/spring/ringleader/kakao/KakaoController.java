package umc.spring.ringleader.kakao;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.ringleader.config.BaseResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;


    @PostMapping("/login/kakao")
    public BaseResponse<String> loginByKakao(@RequestHeader String accessToken) {
        if (accessToken == null) {
            return new BaseResponse<>("error");
        }
        log.debug("[ACCESS TOKEN] : {}", accessToken);

        String accessTokenJsonData = kakaoService.getAccessTokenJsonData(accessToken);

        if(accessTokenJsonData=="error") {
            return new BaseResponse<>("error");
        }
        return new BaseResponse<>(accessTokenJsonData);


    }

}
