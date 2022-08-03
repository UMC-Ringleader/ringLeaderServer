package umc.spring.ringleader.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.login.DTO.PostLoginReq;
import umc.spring.ringleader.login.DTO.PostSignupReq;
import umc.spring.ringleader.login.DTO.PostUserDetailReq;

import javax.sql.DataSource;

@Repository
public class LoginDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /**
     * 이메일로 비밀번호 조회 / 로그인
     *
     * @param email
     * @return
     */
    public String getPwdByEmail(String email) {
        String getPwdQuery = "select password from User where email = ?";
        return this.jdbcTemplate.queryForObject(getPwdQuery, String.class, email);
    }

    public Integer getUserIdByEmail(String email) {
        String getUserIdQuery = "select userId from User where email = ?";
        return this.jdbcTemplate.queryForObject(getUserIdQuery, Integer.class, email);
    }

    /**
     * 비밀번호 일치하는지 조회 / 로그인
     *
     * @param password
     * @return
     */
    public Integer correspondPwd(String password) {
        String getUserIdQuery = "select userId from User where password = ?";

        // 비밀번호 일치하는 경우
        if (getUserIdQuery != null) {
            return this.jdbcTemplate.queryForObject(getUserIdQuery, int.class, password);
        }
        // 비밀번호 일치하지 않는 경우
        else {
            return -1;
        }
    }

    /**
     * 이메일 중복 검사 / 회원가입
     * @param email
     * @return
*/
    public boolean correspondEmail(String email) {
        // 중복되는 이메일이 존재하면 1, 없다면 0
        String getEmailOverlapQuery = "select exists(select userId from User where email = ?)";
        Integer flag = this.jdbcTemplate.queryForObject(getEmailOverlapQuery, Integer.class, email);
        return flag == 1;
    }

    /**
     * 닉네임 중복 검사 / 회원가입
     * @param nickname
     * @return
     */
    public boolean correspondNickname(String nickname) {
        // 중복되는 닉네임이 존재하면 1, 없다면 0
        String getNicknameOverlapQuery = "select exists(select userId from User where nickname = ?)";
        Integer flag = this.jdbcTemplate.queryForObject(getNicknameOverlapQuery, Integer.class, nickname);

        return flag == 1;
    }

    public int saveUser(PostSignupReq req) {
        String saveUserQuery = "insert into User ("
                + "email, password"
                + ") VALUES (?,?)"; // 실행될 동적 쿼리문
        Object[] saveUserParams = new Object[]{
                req.getEmail(),
                req.getPwd(),
        }; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(saveUserQuery, saveUserParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 User id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class); //UserId 반환
    }

    public int saveUserDetail(int userId, PostUserDetailReq req) {
        String modifyUserDetailQuery = "update User set "
                + "nickName =?,"
                + "image =?"
                + "where userId =?"; // 해당 userId를 만족하는 User의 닉네임과 이미지주소 추가
        Object[] modifyUserDetailParams = new Object[]{
                req.getNickname(),
                req.getImgUrl(),
                userId
        };

        return this.jdbcTemplate.update(modifyUserDetailQuery, modifyUserDetailParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public boolean isExistEmail(String email) {
        String findUserByEmailQuery = "select exists(select userId from User where email = ?)";
        Integer flag = this.jdbcTemplate.queryForObject(findUserByEmailQuery, Integer.class, email);
        return flag == 1;
    }
}