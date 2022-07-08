package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.ringleader.config.BaseResponse;

@RestController
public class SearchApiController {

    private final SearchApiService searchApiService;

    @Autowired
    public SearchApiController(SearchApiService searchApiService) {
        this.searchApiService = searchApiService;
    }

    @GetMapping("/search/{keyword}")
    public BaseResponse<SearchResponseDto> getLocal(@PathVariable("keyword") String keyword) {
        return searchApiService.searchLocal(keyword);
    }
}
