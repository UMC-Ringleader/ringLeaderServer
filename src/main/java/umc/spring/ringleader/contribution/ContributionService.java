package umc.spring.ringleader.contribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.contribution.model.ContributionRanking;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.contribution.model.ContributionWithNickNameByReviewId;
import umc.spring.ringleader.region.RegionService;

import java.util.List;
import java.util.Optional;

import static umc.spring.ringleader.config.Constant.DAILY_FIRST_LOGIN_COMPENSATION;

@Service
@EnableScheduling
public class ContributionService {

    private final ContributionRepository repository;
    private final RegionService regionService;

    @Autowired
    public ContributionService(ContributionRepository repository, RegionService regionService) {
        this.repository = repository;
        this.regionService = regionService;
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

    public void initializeUserRegionContribution(int userId) {
        repository.initializeUserRegionContribution(userId, regionService.getAllRegion());

    }

    /*
    매일 0시 0분 0초에 accessed 열을 전부 false 로 만드는 메소드
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void initializeAccessedPerDay() {
        repository.initializeAccessedToFalse();
    }


    public void firstLogin(int userId,int regionId){
        boolean accessed = repository.getAccessed(userId, regionId);
        if (accessed == false) {
            repository.updateAccessed(userId, regionId);
            repository.updateContribution(userId, regionId, DAILY_FIRST_LOGIN_COMPENSATION);
        }
    }
}
