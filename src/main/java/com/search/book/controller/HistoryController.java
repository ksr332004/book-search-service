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
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<?> getKeywordRankSearch() {
        return ResponseEntity.ok().body(historyService.getKeywordRank());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserHistorySearch(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        return ResponseEntity.ok().body(historyService.getUserHistory(currentUser, pageable));
    }

}
