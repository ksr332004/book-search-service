package com.search.book.service;

import com.search.book.entity.History;
import com.search.book.model.Keyword;
import com.search.book.repository.HistoryRepository;
import com.search.book.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Async
    public void saveUserHistory(UserPrincipal currentUser, String query) {
        historyRepository.save(History.builder().keyword(query).userId(currentUser.getId()).build());
    }

    public Page<History> getUserHistory(UserPrincipal currentUser, Pageable pageable) {
        return historyRepository.findAllByUserId(pageable, currentUser.getId());
    }

    public List<Keyword> getKeywordRank() {
        return historyRepository.findKeyword10Rank(PageRequest.of(0, 10)).getContent();
    }

}
