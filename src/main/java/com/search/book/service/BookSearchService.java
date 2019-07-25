package com.search.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.search.book.dto.BookSearchRequest;
import com.search.book.dto.BookSearchResponse;
import com.search.book.dto.KakaoBookResponse;
import com.search.book.dto.NaverBookResponse;
import com.search.book.exception.BusinessException;
import com.search.book.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSearchService {

    @Value("${app.api.kakao.url}")
    private String KAKAO_API_URL;
    @Value("${app.api.kakao.key}")
    private String KAKAO_API_KEY;

    @Value("${app.api.naver.url}")
    private String NAVER_API_URL;
    @Value("${app.api.naver.client-id}")
    private String NAVER_CLIENT_ID;
    @Value("${app.api.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    private final RestTemplate restTemplate;

    @HystrixCommand(commandKey = "getKakaoBookSearchResult", fallbackMethod = "getNaverBookSearchResult")
    public ResponseEntity<BookSearchResponse> getKakaoBookSearchResult(BookSearchRequest request) {
        KakaoBookResponse response = restTemplate.exchange(request.getKakaoUrl(KAKAO_API_URL + "/v3/search/book")
                , HttpMethod.GET
                , new HttpEntity<>(getHeaders(SearchType.KAKAO))
                , KakaoBookResponse.class).getBody();

        if (Objects.isNull(response)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(BookSearchResponse.kakaoBookResponseMapper(response, request));
    }

    public ResponseEntity<BookSearchResponse> getNaverBookSearchResult(BookSearchRequest request) {
        NaverBookResponse response;

        try {
            response = restTemplate.exchange(request.getNaverUrl(NAVER_API_URL + "/v1/search/book.json")
                    , HttpMethod.GET
                    , new HttpEntity<>(getHeaders(SearchType.NAVER))
                    , NaverBookResponse.class).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("================================================");
            log.error("Headers: {}", e.getResponseHeaders());
            log.error("Response Status : {}", e.getStatusCode());
            log.error("Request body: {}", e.getMessage());
            log.error("================================================");
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(BookSearchResponse.naverResponseMapper(Objects.requireNonNull(response)));
    }

    private HttpHeaders getHeaders(SearchType searchType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if (searchType.equals(SearchType.KAKAO)) {
            httpHeaders.add("Authorization", "KakaoAK " + KAKAO_API_KEY);
        } else {
            httpHeaders.add("X-Naver-Client-Id", NAVER_CLIENT_ID);
            httpHeaders.add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
        }
        return httpHeaders;
    }

    private enum SearchType {
        KAKAO,
        NAVER
    }

}
