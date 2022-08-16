package umc.spring.ringleader.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.contribution.ContributionService;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.contribution.model.Grade;
import umc.spring.ringleader.user.dto.GetUserRes;
import umc.spring.ringleader.user.dto.GetUserTemp;
import umc.spring.ringleader.user.dto.UserRegionGrade;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ContributionService contributionService;

    public GetUserRes getUserDetail(int userId) {
        GetUserTemp userDetailTemp = userDao.getUserDetail(userId);
        List<UserRegionGrade> userRegionGrades = new ArrayList<>();
        List<ContributionWithLocation> contributionWithLocation = contributionService.getContributionWithLocation(userId);
        for (ContributionWithLocation withLocation : contributionWithLocation) {
            Grade grade = contributionService.getGradeByContribution((int) withLocation.getContribution());
            userRegionGrades.add(new UserRegionGrade(withLocation, grade));
        }
        return new GetUserRes(userDetailTemp.getUserId(),
                userDetailTemp.getNickName(),
                userDetailTemp.getImage(),
                userRegionGrades);
    }
}
