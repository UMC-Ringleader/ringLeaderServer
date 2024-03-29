package umc.spring.ringleader.search;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.search.model.SearchResponseDto;
import umc.spring.ringleader.search.model.SearchResultRegion;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public List<GetSearchListRes> searchLocal(String keyword) throws BaseException {
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

            List<PostSearchResultReq> collect = items.stream().map(SearchResponseDto.Item::itemToDto).collect(Collectors.toList());

            List<GetSearchListRes> searchedList = new ArrayList<>();
            for (PostSearchResultReq p : collect) {
                if (searchApiRepository.checkingExistingWithTitleAndAddress(p.getTitle().replace("<b>", "").replace("</b>", ""), p.getAddress()) == 0) {
                    int searchedResultList = searchApiRepository.updateSearchedResultList(p, getRegionId(p));
                    searchedList.add(new GetSearchListRes(searchedResultList, p.getTitle().replace("<b>", "").replace("</b>", ""), p.getAddress(),defineCategory(p.getCategory())));
                } else {
                    GetSearchListRes byTitleAndAddress = searchApiRepository.findByTitleAndAddress(p.getTitle().replace("<b>", "").replace("</b>", ""), p.getAddress());
                    byTitleAndAddress.setCategory(defineCategory(byTitleAndAddress.getCategory()));
                    searchedList.add(byTitleAndAddress);
                }
            }
            return searchedList;
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

    public List<GetSearchListRes> getSavedList(int regionId) throws BaseException {
        try {
            return searchApiRepository.findAllSavedList(regionId);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_GET_REGION_REVIEW_SEARCH_LIST_IN_SERVER);
        }
    }

    public List<GetSearchListRes> searchDataBase(String keyword) {
        List<GetSearchListRes> byTitle = searchApiRepository.findByTitle(keyword);
        byTitle.stream().forEach(
                a -> a.setCategory(defineCategory(a.getCategory())));
        return byTitle;
    }

    public List<GetSearchListRes> deduplicationList(List<GetSearchListRes> api, List<GetSearchListRes> database) {
        api.addAll(database);
        List<GetSearchListRes> distinct = deduplication(api, GetSearchListRes::getRRDId);
        return distinct;
    }

    public static <T> List<T> deduplication(final List<T> list, Function<? super T, ?> key) {
        return list.stream()
                .filter(deduplication(key))
                .collect(Collectors.toList());
    }

    private static <T> Predicate<T> deduplication(Function<? super T,?> key) {
        final Set<Object> set = ConcurrentHashMap.newKeySet();
        return predicate -> set.add(key.apply(predicate));
    }

    public String defineCategory(String category) {
        if (category.contains("음식점") || category.contains("한식") || category.contains("중식") || category.contains("일식") || category.contains("양식") || category.contains("분식")) {
            return "음식점";
        } else if (category.contains("술집") || category.contains("주점")) {
            return "주점";
        } else if (category.contains("카페")) {
            return "카페";
        } else if (category.contains("문화")||category.contains("명소")||category.contains("광장")||category.contains("공원")) {
            return "문화";
        } else {
            return "기타";
        }
    }
}
