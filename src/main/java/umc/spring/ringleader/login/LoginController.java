package umc.spring.ringleader.login;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.login.DTO.PostLoginReq;
import umc.spring.ringleader.login.DTO.PostLoginRes;
import umc.spring.ringleader.login.DTO.PostSignupReq;
import umc.spring.ringleader.login.DTO.PostSignupRes;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    /**
     * 로그인 API
     * 1. 이메일 대조
     * 2. PW 대조
     * 3. 성공시 userId 리턴
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) {
        log.info("[Login][POST] : 로그인 API / email = {}", postLoginReq.getEmail());
        PostLoginRes postLoginRes = loginService.retrievePwdByEmail(postLoginReq);
        return new BaseResponse<>(postLoginRes);
        /**try{
            if(postLoginReq.getEmail() == null)
            {
                return new BaseResponse<>(POST_Login_EMPTY_EMAIL);
            }

            if(postLoginReq.getPwd() == null)
            {
                return new BaseResponse<>(POST_Login_EMPTY_PWD);
            }
        }
        */
    }

    /**
     * 회원가입 API
     * 1. 이메일 중복 검사
     * 2. 비밀번호, 비밀번호 확인 대조
     */
    @ResponseBody
    @PostMapping
    public BaseResponse<PostSignupRes> signup(@RequestBody PostSignupReq postSignupReq) {
        log.info("[Login][POST] : 회원가입 API / email = {}", postSignupReq.getEmail());
        PostSignupRes postSignupRes = loginService.retrieveEmail(postSignupReq);
        return new BaseResponse<>(postSignupRes);
        /**
        if (postSignupReq.getEmail() == null) {
            return new BaseResponse<>(POST_SIGNUP_EMPTY_EMAIL);
        }
        if (!isRegexEmail(postSignupReq.getEmail())) {
            return new BaseResponse<>(POST_SIGNUP_INVALID_EMAIL);
        }

        if (postSignupReq.getPwd() != postSignupReq.getRe_pwd()) {
            return new BaseResponse<>(POST_SIGNUP_MISMATCH_PASSWORD);
        }
         */
    }
}
