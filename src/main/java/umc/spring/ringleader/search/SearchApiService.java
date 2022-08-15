package umc.spring.ringleader.search;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.search.model.SearchResponseDto;
import umc.spring.ringleader.search.model.SearchResultRegion;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static umc.spring.ringleader.config.BaseResponseStatus.*;

@Service
@Slf4j
public class SearchApiService {

    private final RestTemplate restTemplate;
    private final SearchApiRepository searchApiRepository;
    private final SearchResultRegion searchResultRegion = new SearchResultRegion();

    public SearchApiService(RestTemplate restTemplate, SearchApiRepository searchApiRepository) {
        this.restTemplate = restTemplate;
        this.searchApiRepository = searchApiRepository;
    }

    private final String CLIENT_ID = "FKlT_BMkmAEiurtLSm2x";
    private final String CLIENT_SECRET = "Yo6xo7jkDT";

    public List<SearchResponseDto.Item> searchLocal(String keyword) throws BaseException {

        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://openapi.naver.com")
                    .path("/v1/search/local.json")
                    .queryParam("query", keyword)
                    .queryParam("display", 5)
                    .queryParam("start", 1)
                    .queryParam("sort", "random")
                    .encode(Charset.forName("UTF-8"))
                    .encode()
                    .build()
                    .toUri();

            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .header("X-Naver-Client-Id", CLIENT_ID)
                    .header("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build();

            log.info("uri : {}", uri);
            ResponseEntity<SearchResponseDto> result = restTemplate.exchange(req, SearchResponseDto.class);
            List<SearchResponseDto.Item> items = Arrays.stream(result.getBody().getItems())
                    .filter(a -> a.getAddress().contains("서울"))
                    .collect(Collectors.toList());
            items.stream().map(SearchResponseDto.Item::itemToDto)
                    .forEach(a -> searchApiRepository.updateSearchedResultList(a, getRegionId(a)));
            return items;
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_SEARCH_IN_SERVER);
        }


    }

    public String createSearchedResult(PostSearchResultReq postSearchResultReq) throws BaseException {
        try {
            int regionId = getRegionId(postSearchResultReq);
            searchApiRepository.updateSearchedResult(postSearchResultReq.getTitle().replace("<b>", "").replace("</b>", ""), postSearchResultReq.getCategory(),
                    postSearchResultReq.getAddress(), postSearchResultReq.getRoadAddress(), postSearchResultReq.getMapx(), postSearchResultReq.getMapy(), regionId);
            return "선택결과가 저장되었습니다.";
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_SEARCH_RESULT_IN_SERVER);
        }

    }

    private int getRegionId(PostSearchResultReq postSearchResultReq) {

        String address = postSearchResultReq.getAddress();
        String roadAddress = postSearchResultReq.getRoadAddress();

        String s = searchResultRegion.getSpecificRoadArea().stream()
                .filter(roadAddress::contains)
                .findAny()
                .orElse(searchResultRegion.getSpecificArea().stream()
                        .filter(address::contains)
                        .findAny()
                        .orElse(searchResultRegion.getArea().stream()
                                .filter(address::contains)
                                .findAny().get()));

        return searchApiRepository.findRegionId(s);
    }

    public List<GetSearchListRes> getSavedList() {
        return searchApiRepository.findAllSavedList();
    }
}
