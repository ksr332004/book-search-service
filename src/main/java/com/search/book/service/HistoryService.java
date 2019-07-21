package com.search.book.service;

import com.search.book.entity.History;
import com.search.book.entity.User;
import com.search.book.exception.ResourceNotFoundException;
import com.search.book.model.Keyword;
import com.search.book.repository.HistoryRepository;
import com.search.book.repository.UserRepository;
import com.search.book.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        ).orElseThrow( () -> new ResourceNotFoundException("user", "User", null) );
    }

    public Page<History> getUserKeywordHistory(UserPrincipal currentUser, Pageable pageable) {
        Optional<User> user = userRepository.findById(currentUser.getId());
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("user", "User", null);
        }

        return historyRepository.findAllByUserIdOrderByRegistrationDateDesc(pageable, user.get().getId());
    }

    public List<Keyword> getKeywordRank() {
        return historyRepository.findKeyword10Rank(PageRequest.of(0, 10)).getContent();
    }

}
