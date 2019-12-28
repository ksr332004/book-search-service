package com.search.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.search.book.dto.BookDTO;
import com.search.book.dto.KakaoBookDTO;
import com.search.book.dto.NaverBookDTO;
import com.search.book.exception.BusinessException;
import com.search.book.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<BookDTO.Res> getKakaoBookSearchResult(BookDTO.Req req) {
        KakaoBookDTO.Res kakaoBookRes = restTemplate.exchange(req.getKakaoUrl(KAKAO_API_URL + "/v3/search/book")
                , HttpMethod.GET
                , new HttpEntity<>(getHeaders(SearchType.KAKAO))
                , KakaoBookDTO.Res.class).getBody();

        if (Objects.isNull(kakaoBookRes)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(BookDTO.Res.kakaoBookResponseMapper(kakaoBookRes, req));
    }

    public ResponseEntity<BookDTO.Res> getNaverBookSearchResult(BookDTO.Req req) {
        NaverBookDTO.Res naverBookRes;

        try {
            naverBookRes = restTemplate.exchange(req.getNaverUrl(NAVER_API_URL + "/v1/search/book.json")
                    , HttpMethod.GET
                    , new HttpEntity<>(getHeaders(SearchType.NAVER))
                    , NaverBookDTO.Res.class).getBody();
        } catch (final HttpClientErrorException e) {
            log.error("================================================");
            log.error("Headers: {}", e.getResponseHeaders());
            log.error("Response Status : {}", e.getStatusCode());
            log.error("Request body: {}", e.getMessage());
            log.error("================================================");
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(BookDTO.Res.naverResponseMapper(Objects.requireNonNull(naverBookRes)));
    }

    private HttpHeaders getHeaders(SearchType searchType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
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
