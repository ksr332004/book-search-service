package com.search.book.dto;

import com.search.book.model.kakao.Document;
import com.search.book.model.kakao.Meta;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KakaoBookResponse {
    private Meta meta;
    private List<Document> documents = new ArrayList<>();
}