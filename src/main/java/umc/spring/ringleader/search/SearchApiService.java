package umc.spring.ringleader.search;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import umc.spring.ringleader.config.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Service
@Slf4j
public class SearchApiService {

    public SearchApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final RestTemplate restTemplate;
    private final String CLIENT_ID = "FKlT_BMkmAEiurtLSm2x";
    private final String CLIENT_SECRET = "Yo6xo7jkDT";



    public BaseResponse<SearchResponseDto> searchLocal(String keyword) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",keyword)
                .queryParam("display",5)
                .queryParam("start",1)
                .queryParam("sort","random")
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
        return new BaseResponse<>(result.getBody());
    }

}
