package umc.spring.ringleader.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.login.DTO.PostLoginReq;
import umc.spring.ringleader.login.DTO.PostLoginRes;
import umc.spring.ringleader.login.DTO.PostSignupReq;
import umc.spring.ringleader.login.DTO.PostSignupRes;

@RestController
@RequestMapping("/login")
public class LoginController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final LoginService LoginService;




    public LoginController(LoginService LoginService) {
        this.LoginService = LoginService;
    }

    @ResponseBody
    @PostMapping("/login")
    /**
     * 로그인 API
     * 1. 이메일 대조
     * 2. PW 대조
     * 3. 성공시 userId 리턴
      */

    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq) {

        PostLoginRes postLoginRes = LoginService.retrievePwdByEmail(postLoginReq);
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

    @ResponseBody
    @PostMapping
    /**
     * 회원가입 API
     * 1. 이메일 중복 검사
     * 2. 비밀번호, 비밀번호 확인 대조
     */

    public BaseResponse<PostSignupRes> signup(@RequestBody PostSignupReq postSignupReq) {
        PostSignupRes postSignupRes = LoginService.retrieveEmail(postSignupReq);
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
