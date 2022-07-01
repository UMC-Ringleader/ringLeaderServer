package umc.spring.ringleader.contribution1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;
import umc.spring.ringleader.contribution1.model.ContributionWithNickNameByReviewId;

import java.util.List;

@Service
public class ContributionService {

    private final ContributionRepository repository;

    @Autowired
    public ContributionService(ContributionRepository repository) {
        this.repository = repository;
    }

    /*
    int 형 userId 로 지역명 + 기여도를 List 로 반환 기여도가 큰거부터 정렬되어 있음
     */
    public List<ContributionWithLocation> getContributionWithLocation(int userId){
        return repository.getContributionWithLocationByUserId(userId);
    }

    /*
    int 형 reviewId 로 닉네임 + 기여도를 반환 메인페이지에서 닉네임, 기여도 에 사용
     */
    public ContributionWithNickNameByReviewId ContributionWithNickNameByReviewId(int reviewId){
        return repository.getCWNBR(reviewId);
    }


}
