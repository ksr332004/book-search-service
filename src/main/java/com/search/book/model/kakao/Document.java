package com.search.book.model.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Document {
    private String title;
    private String contents;
    private String url;
    private String isbn;
    private String datetime;
    private String publisher;
    private Integer price;
    private Integer salePrice;
    private String thumbnail;
    private String status;
    @JsonProperty("authors")
    private List<String> authors = new ArrayList<>();
    @JsonProperty("translators")
    private List<String> translators = new ArrayList<>();

    public String getAuthorString() {
        if (authors.isEmpty()) {
            return "";
        }

        String author;
        if (authors.size() > 1) {
            author = authors.get(0) + " 외 " + (authors.size() - 1) + " 명 지음";
        } else {
            author = authors.get(0) + " 지음";
        }

        return author;
    }

    public String getDatetimeFormat() {
        if (datetime.isEmpty()) {
            return "";
        }

        return datetime.substring(0, 10);
    }
}