package umc.spring.ringleader.Notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.Notice.DTO.NoticeRes;
import umc.spring.ringleader.Notice.DTO.PostNoticeReq;
import umc.spring.ringleader.Notice.DTO.PostNoticeRes;

import java.util.List;

@Slf4j
@Service
public class NoticeService {
    private final NoticeDao noticeDao;

    @Autowired
    public NoticeService(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    // Notice 등록
    public PostNoticeRes saveNotice(PostNoticeReq req) {
        int savedId = noticeDao.saveNotice(req);

        return new PostNoticeRes(savedId, req.getTitle());
    }

    // Notice 조회
    public List<NoticeRes> getNotices() {
        List<NoticeRes> noticeTmp = noticeDao.getNotices(); // error
        return noticeTmp;
    }
}
