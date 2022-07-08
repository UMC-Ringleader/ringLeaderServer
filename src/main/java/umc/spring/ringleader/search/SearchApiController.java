package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.search.model.SearchResponseDto;

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

    @PostMapping("/search/create")
    public BaseResponse<String> createSearchedResult(@RequestBody PostSearchResultReq postSearchResultReq) {
        String result = searchApiService.createSearchedResult(postSearchResultReq);
        return new BaseResponse<>(result);
    }
}
