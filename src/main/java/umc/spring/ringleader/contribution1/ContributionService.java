package umc.spring.ringleader.contribution1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;

import java.util.List;

@Service
public class ContributionService {

    private final ContributionRepository repository;

    @Autowired
    public ContributionService(ContributionRepository repository) {
        this.repository = repository;
    }

    public List<ContributionWithLocation> getContributionWithLocation(int userId){
        return repository.getContributionWithLocationByUserId(userId);
    }


}
