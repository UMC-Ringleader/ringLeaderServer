package umc.spring.ringleader.notice1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.notice1.DTO.NoticeRes;
import umc.spring.ringleader.notice1.DTO.PostNoticeReq;
import umc.spring.ringleader.notice1.DTO.PostNoticeRes;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;

import javax.transaction.Transaction;

import java.util.List;

import static umc.spring.ringleader.config.BaseResponseStatus.POST_Login_EMPTY_EMAIL;
import static umc.spring.ringleader.config.BaseResponseStatus.POST_Login_EMPTY_EMAIL;

@Slf4j
@RestController
@RequestMapping("/app/Notice")
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    /**
     * Notice 등록 메서드
     *
     * @param postNoticeReq
     * @return PostNoticeRes
     */
    @ApiOperation(value = "Notice 등록")
    @ApiParam(name = "Notice 정보", required = true)
    @ResponseBody
    @PostMapping("/")
    public BaseResponse<PostNoticeRes> addNotice(@RequestBody PostNoticeReq postNoticeReq) {

        /**
         예외
         Not Null
         1. Title
         2. Content
         */
        try {
            if (postNoticeReq.getTitle() == null) {
                throw new BaseException(POST_Login_EMPTY_EMAIL);
            } else if (postNoticeReq.getContent() == null) {
                throw new BaseException(POST_Login_EMPTY_EMAIL);
            }
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        PostNoticeRes res = noticeService.saveNotice(postNoticeReq);
        return new BaseResponse<>(res);
    }

    /**
     * Notice 조회 (최신순으로 정렬)
     * @return List<NoticeRes>
     */
    @ApiOperation(value = "공지 최신순 조회")
    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<NoticeRes>> getNoticesLately() {
        log.info("[Notice][GET] : 리뷰 조회 (최신순으로 정렬)");

        List<NoticeRes>getNotices = noticeService.getNotices(); // error

        return new BaseResponse<>(getNotices);
    }
}