package com.search.book.service;

import com.search.book.entity.History;
import com.search.book.model.Keyword;
import com.search.book.repository.HistoryRepository;
import com.search.book.repository.UserRepository;
import com.search.book.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Async
    @Transactional
    public void saveUserHistory(UserPrincipal currentUser, String query) {
        userRepository.findById(currentUser.getId()).map(
                u -> historyRepository.save(History.builder().keyword(query).userId(u.getId()).build())
        ).orElseThrow( () -> new BadCredentialsException("사용자 정보가 없습니다.") );
    }

    public Page<History> getUserHistory(UserPrincipal currentUser, Pageable pageable) {
        return userRepository.findById(currentUser.getId()).map(
                u -> historyRepository.findAllByUserIdOrderByRegistrationDateDesc(pageable, u.getId())
        ).orElseThrow( () -> new BadCredentialsException("사용자 정보가 없습니다.") );
    }

    public List<Keyword> getKeywordRank() {
        return historyRepository.findKeyword10Rank(PageRequest.of(0, 10)).getContent();
    }

}
