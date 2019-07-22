package com.search.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequest {

    @NotNull
    private String query;              // 검색어
    private String sort = "accuracy";  // 정렬
    @Min(1)
    @Max(100)
    private Integer page = 1;   // 현재 페이지 번호(start)
    @Min(10)
    @Max(50)
    @Builder.Default
    private Integer size = 10;  // 보여지는 문서의 개수(display)
    private String target;      // 검색 필드

    @Getter
    @AllArgsConstructor
    public enum SortGroup {
        sim("accuracy"),   // 정확도순
        date("latest");    // 최신순

        private String sort;

        public static SortGroup findBySortGroup(String uri) {
            return Arrays.stream(SortGroup.values())
                    .filter( s -> s.sort.equals(uri) )
                    .findFirst()
                    .orElse(SortGroup.sim);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum TargetGroup {
        d_titl("title"),       // 제목 검색
        d_isbn("isbn"),        // ISBN 검색
        d_publ("publisher"),   // 출판사 검색
        d_auth("person");      // 인명 검색

        private String target;

        public static TargetGroup findByTargetGroup(String uri) {
            return Arrays.stream(TargetGroup.values())
                    .filter( t -> t.target.equals(uri) )
                    .findFirst()
                    .orElse(TargetGroup.d_titl);
        }
    }

    public String getKakaoUrl(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        builder.queryParam("query", this.query);

        if (StringUtils.hasText(this.sort)) {
            builder.queryParam("sort", this.sort);
        }
        if (this.page != null) {
            builder.queryParam("page", this.page);
        }
        if (this.size != null) {
            builder.queryParam("size", this.size);
        }
        if (StringUtils.hasText(this.target)) {
            builder.queryParam("target", this.target);
        }

        return builder.build().toString();
    }

    public String getNaverUrl(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (this.target == null) {
            builder.queryParam("query", this.query);
        } else {
            builder.queryParam(TargetGroup.findByTargetGroup(this.target).toString(), this.query);
        }

        if (StringUtils.hasText(this.sort)) {
            builder.queryParam("sort", SortGroup.findBySortGroup(this.sort).toString());
        }
        if (this.page != null) {
            builder.queryParam("start", this.page);
        }
        if (this.size != null) {
            builder.queryParam("display", this.size);
        }

        return builder.build().toString();
    }

}
