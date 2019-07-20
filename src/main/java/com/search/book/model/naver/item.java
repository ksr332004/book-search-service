package com.search.book.model.naver;

import lombok.*;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class item {
    private String title;
    private String link;
    private String image;
    private String author;
    private Integer price;
    private Integer discount;
    private String publisher;
    private String isbn;       // Integer일 경우 오류 발생
    private String description;
    private Date pubdate;

    public String getPubdateString() {
        if (StringUtils.isEmpty(pubdate)) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(pubdate);
    }
}
