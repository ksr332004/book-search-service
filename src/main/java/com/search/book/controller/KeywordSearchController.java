package com.search.book.controller;

import com.search.book.security.CurrentUser;
import com.search.book.security.UserPrincipal;
import com.search.book.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/keyword")
@RequiredArgsConstructor
public class KeywordSearchController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<?> getKeywordSearch() {
        return ResponseEntity.ok().body(historyService.getKeywordRank());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserKeywordSearch(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        return ResponseEntity.ok().body(historyService.getUserKeywordHistory(currentUser, pageable));
    }

}
