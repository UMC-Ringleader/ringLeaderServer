package umc.spring.ringleader.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.login.DTO.PostLoginReq;
import umc.spring.ringleader.login.DTO.PostLoginRes;
import umc.spring.ringleader.login.DTO.PostSignupReq;
import umc.spring.ringleader.login.DTO.PostSignupRes;
import umc.spring.ringleader.login.DTO.PostUserDetailReq;

@Service
public class LoginService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    /**
     * 이메일 통해 비밀번호 조회
     */
    public PostLoginRes retrievePwdByEmail(PostLoginReq postLoginReq){
        // 입력 받은 이메일과 대응하는 패스워드 조회
        String checkPwd = loginDao.getPwdByEmail(postLoginReq.getEmail());

        // 패스워드 일치하는지 비교
        if(checkPwd.equals(postLoginReq.getPwd())) {
            int checkUserId = loginDao.getUserIdByEmail(postLoginReq.getEmail());
            return new PostLoginRes(checkUserId);
        }
        else {
            return null;
        }
    }

    /**
     * 이메일 중복 검사 -> 비밀번호 검사
     */
    public PostSignupRes retrieveEmail(PostSignupReq postSignupReq){
        // 비밀번호와 비밀번호 확인이 동일한 경우
        if(postSignupReq.getPwd().equals(postSignupReq.getRe_pwd()))
        {
            // 이메일 중복되는 경우
            if(loginDao.isCorrespondEmail(postSignupReq.getEmail())) {
                return null;
            }
            // 이메일 중복되지 않는 경우
            else{
                return new PostSignupRes(loginDao.saveUser(postSignupReq));
            }
        }
        // 비밀번호와 비밀번호 확인이 동일하지 않은 경우
        else {
            return null;
        }
    }

    public String saveUserDetail(int userId, PostUserDetailReq req) {
        int updateCode = loginDao.saveUserDetail(userId, req);
        if (updateCode == 1) {
            return "회원 가입 완료!";
        }

        return "회원 가입 실패";
    }
}