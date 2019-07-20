package com.search.book.dto;

import com.search.book.model.naver.item;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NaverBookResponse {
    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<item> items;
}
