package com.search.book.service;

import com.search.book.entity.History;
import com.search.book.entity.User;
import com.search.book.exception.ResourceNotFoundException;
import com.search.book.repository.HistoryRepository;
import com.search.book.repository.UserRepository;
import com.search.book.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Async
    public void saveUserHistory(UserPrincipal currentUser, String query) {
        // 사용자 검색어 저장
        userRepository.findById(currentUser.getId()).map(
                u -> {
                    History history = historyRepository.findByUserAndKeyword(u, query);
                    Optional.ofNullable(history).map(
                            k -> {
                                history.setKeyword(query);
                                history.setUser(u);
                                history.setRegistrationDate(LocalDateTime.now());
                                return historyRepository.save(history);
                            }
                    ).orElseGet( () -> historyRepository.save(History.builder().keyword(query).user(u).build()) );
                    return u;
                }
        ).orElseThrow( () -> new ResourceNotFoundException("user", "User", "Object") );
    }

    // TODO : 페이징 처리 하기
    public List<History> getUserKeywordHistory(UserPrincipal currentUser) {
        Optional<User> user = userRepository.findById(currentUser.getId());
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("user", "User", "Object");
        }

        return historyRepository.findAllByUserOrderByRegistrationDateDesc(user.get());
    }

}
