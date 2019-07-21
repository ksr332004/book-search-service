package com.search.book.service;

import com.search.book.entity.Keyword;
import com.search.book.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Async
    public void saveKeyword(String query) {
        // 검색어 순위 저장
        // TODO : 동시성 처리 필요
        Keyword keyword = keywordRepository.findByKeyword(query);
        Optional.ofNullable(keyword).map(
                k -> {
                    k.setCount(k.getCount()+1);
                    return keywordRepository.save(k);
                }
        ).orElseGet( () -> keywordRepository.save(Keyword.builder().keyword(query).count(1L).build()) );
    }

    public List<Keyword> getKeywordSearch() {
        return keywordRepository.findTop10ByOrderByCountDesc();
    }

}
