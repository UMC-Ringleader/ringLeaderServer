package umc.spring.ringleader.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.user.dto.GetUserRes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUserDetail(@PathVariable int userId) {
        GetUserRes userDetail = userService.getUserDetail(userId);
        return new BaseResponse<>(userDetail);
    }
}
