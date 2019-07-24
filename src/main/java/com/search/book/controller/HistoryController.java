package com.search.book.controller;

import com.search.book.entity.History;
import com.search.book.model.Keyword;
import com.search.book.security.CurrentUser;
import com.search.book.security.UserPrincipal;
import com.search.book.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/keyword")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @Cacheable(cacheNames = "keywordRank")
    @GetMapping
    public ResponseEntity<List<Keyword>> getKeywordRankSearch() {
        return ResponseEntity.status(HttpStatus.OK).body(historyService.getKeywordRank());
    }

    @GetMapping("/user")
    public ResponseEntity<Page<History>> getUserHistorySearch(@CurrentUser UserPrincipal currentUser,
                                                              @PageableDefault(sort = "registrationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(historyService.getUserHistory(currentUser, pageable));
    }

}
