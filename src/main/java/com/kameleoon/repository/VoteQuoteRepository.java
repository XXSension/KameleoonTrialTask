package com.kameleoon.repository;

import com.kameleoon.entity.Quote;
import com.kameleoon.entity.VoteQuote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface VoteQuoteRepository extends JpaRepository<VoteQuote, Long> {
    @Transactional
    @Modifying
    void deleteByQuote(Quote quote);

    Optional<VoteQuote> findByQuoteIdAndUserId(Long quoteId, Long userId);
}
