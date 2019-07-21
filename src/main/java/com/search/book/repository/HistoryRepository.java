package com.search.book.repository;

import com.search.book.entity.User;
import com.search.book.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    History findByUserAndKeyword(User user, String keyword);
    List<History> findAllByUserOrderByRegistrationDateDesc(User user);
}
