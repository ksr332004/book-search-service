package com.search.book.repository;

import com.search.book.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByKeyword(String keyword);
    List<Keyword> findTop10ByOrderByCountDesc();
}
