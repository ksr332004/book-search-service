package com.search.book.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {

    KEYWORD_RANK("keywordRank", 10, 30, 100);  // 30 sec.

    private String cacheName;
    private int initialCapacity;
    private int expiredAfterWrite;
    private int maximumSize;

}
