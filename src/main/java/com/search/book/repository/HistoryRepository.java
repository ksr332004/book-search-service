package com.search.book.repository;

import com.search.book.entity.History;
import com.search.book.model.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findAllByUserId(Pageable pageable, Long userId);

    @Query(value = "SELECT new com.search.book.model.Keyword(h.keyword, count(h))"
                + " FROM History h"
                + " GROUP BY h.keyword"
                + " ORDER BY count(h) DESC")
    Page<Keyword> findKeyword10Rank(Pageable pageable);
}