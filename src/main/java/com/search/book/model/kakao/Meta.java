package com.search.book.model.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Meta {
    private Integer totalCount;
    private Integer pageableCount; // 의미있는 검색 결과 수
    private Boolean isEnd;
}