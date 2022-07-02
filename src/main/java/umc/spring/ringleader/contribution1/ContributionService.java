package umc.spring.ringleader.contribution1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.contribution1.model.ContributionRanking;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;
import umc.spring.ringleader.contribution1.model.ContributionWithNickNameByReviewId;

import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    private final ContributionRepository repository;

    @Autowired
    public ContributionService(ContributionRepository repository) {
        this.repository = repository;
    }

    //지역별 전체 ranking 조회 (API O)
    public List<ContributionRanking> getRankingByRegionId(int regionId) {
        return repository.getRankingByRegionId(regionId);
    }

    //지역별 전체 ranking 페이지에서 개인 ranking 조회 (API O)
    public Optional<ContributionRanking> getRankingByUserId(int userId,int regionId){
        return repository.getRankingByRegionId(regionId).stream()
                .filter(a -> a.getUserId() == userId)
                .findAny();
    }

    /*
    int 형 userId 로 지역명 + 기여도를 List 로 반환 기여도가 큰거부터 정렬되어 있음(api X)
     */
    public List<ContributionWithLocation> getContributionWithLocation(int userId){
        return repository.getContributionWithLocationByUserId(userId);
    }

    /*
    int 형 reviewId 로 닉네임 + 기여도를 반환 메인페이지에서 닉네임, 기여도 에 사용(api X)
     */
    public ContributionWithNickNameByReviewId ContributionWithNickNameByReviewId(int reviewId){
        return repository.getCWNBR(reviewId);
    }

    /*
    int 형 userId, regionId, n 을 매개변수로 함
    review 내에서 if 문으로 구분하여 사진의 유무를 통해 n 의 값을 인자로 넣어주고 내부에서 contribution 값을 변경함.
     */
    public void contributionUpdate(int userId, int regionId, int n) {
        repository.updateContribution(userId, regionId, n);
    }
}
