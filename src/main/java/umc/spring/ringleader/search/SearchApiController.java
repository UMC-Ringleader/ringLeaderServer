package umc.spring.ringleader.search;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.search.model.SearchResponseDto;

import java.util.List;

@RestController
public class SearchApiController {

    private final SearchApiService searchApiService;

    @Autowired
    public SearchApiController(SearchApiService searchApiService) {
        this.searchApiService = searchApiService;
    }

    @ApiOperation(value = "네이버 Api만 이용하여 상호명 검색 결과")
    @ApiImplicitParam(name = "keyword", value = "상호명", required = true)
    @GetMapping("/search")
    public BaseResponse<List<GetSearchListRes>> getLocal(@RequestParam String keyword) {
        try {
            return new BaseResponse<>(searchApiService.searchLocal(keyword));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "DB 조회 및 결과 없는 경우 네이버 Api 를 이용하여 상호명 검색 결과")
    @ApiImplicitParam(name = "keyword", value = "상호명", required = true)
    @GetMapping("/search/saving")
    public BaseResponse<List<GetSearchListRes>> getSavedList(@RequestParam String keyword) {
        try {
            List<GetSearchListRes> getSearchListRes = searchApiService.searchDataBase(keyword);
            if (getSearchListRes.size() == 0) {
                return new BaseResponse<>(searchApiService.searchLocal(keyword));
            }
            return new BaseResponse<>(getSearchListRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
//    @ApiOperation(value = "검색결과를 DB에 저장")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "title", value = "상호명", required = true),
//            @ApiImplicitParam(name = "category", value = "상호명", required = true),
//            @ApiImplicitParam(name = "address", value = "상호명", required = true),
//            @ApiImplicitParam(name = "roadAddress", value = "상호명", required = true),
//            @ApiImplicitParam(name = "mapx", value = "상호명", required = true),
//            @ApiImplicitParam(name = "mapy", value = "상호명", required = true),
//            @ApiImplicitParam(name = "regionId", value = "Region 식별자", required = true)
//    })
//    @PostMapping("/search/create")
//    public BaseResponse<String> createSearchedResult(@RequestBody PostSearchResultReq postSearchResultReq) {
//        try {
//            String result = searchApiService.createSearchedResult(postSearchResultReq);
//            return new BaseResponse<>(result);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
    @ApiOperation(value = "저장된 상호 목록 최신순 조회")
    @ApiImplicitParam(name = "regionId", value = "지역 식별자", required = true)
    @GetMapping("/search/list/{regionId}")
    public BaseResponse<List<GetSearchListRes>> getSavedList(@PathVariable int regionId) {
        try {
            return new BaseResponse<>(searchApiService.getSavedList(regionId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}

