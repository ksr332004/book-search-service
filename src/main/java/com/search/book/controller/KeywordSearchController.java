package com.search.book.controller;

import com.search.book.security.CurrentUser;
import com.search.book.security.UserPrincipal;
import com.search.book.service.HistoryService;
import com.search.book.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/keyword")
@RequiredArgsConstructor
public class KeywordSearchController {

    private final KeywordService keywordService;
    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<?> getKeywordSearch() {
        return ResponseEntity.ok().body(keywordService.getKeywordSearch());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserKeywordSearch(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(historyService.getUserKeywordHistory(currentUser));
    }

}
