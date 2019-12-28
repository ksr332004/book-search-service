package com.search.book.dto;

import lombok.*;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NaverBookDTO {

    @Getter
    @Setter
    public static class Res {
        private Date lastBuildDate;
        private Integer total;
        private Integer start;
        private Integer display;
        private List<Item> items;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
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
            if (StringUtils.isEmpty(this.pubdate)) {
                return "";
            }

            return new SimpleDateFormat("yyyy-MM-dd").format(this.pubdate);
        }
    }

}
