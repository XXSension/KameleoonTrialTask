package com.kameleoon.service;

import com.kameleoon.dto.voteQuote.VoteQuoteInDTO;
import com.kameleoon.entity.Quote;
import com.kameleoon.entity.VoteQuote;
import com.kameleoon.exception.NotFoundException;
import com.kameleoon.mapper.VoteQuoteMapper;
import com.kameleoon.repository.QuoteRepository;
import com.kameleoon.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kameleoon.exception.ExceptionDescriptions.QUOTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteQuoteService {

    private final QuoteRepository quoteRepository;

    private final VoteQuoteRepository voteQuoteRepository;

    public List<Integer> getScoresHistory(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        return quote.getVotes().stream()
                .sorted(Comparator.comparing(VoteQuote::getUpdatedAt))
                .map(VoteQuoteMapper::voteQuoteToInteger)
                .collect(Collectors.toList());
    }

    public void vote(VoteQuoteInDTO voteQuoteInDTO) {
        Optional<VoteQuote> voteQuote = voteQuoteRepository
                .findByQuoteIdAndUserId(voteQuoteInDTO.getQuoteId(),voteQuoteInDTO.getUserId());
        if (voteQuote.isPresent()) {
            if (voteQuote.get().isVote() && !voteQuoteInDTO.isVote()) {
                updateScoreAndSaveVote(-2, voteQuoteInDTO);
            }
            if (!voteQuote.get().isVote() && voteQuoteInDTO.isVote()) {
                updateScoreAndSaveVote(2, voteQuoteInDTO);
            }
        } else {
            if (voteQuoteInDTO.isVote()) {
                updateScoreAndSaveVote(1,voteQuoteInDTO);
            } else {
                updateScoreAndSaveVote(-1,voteQuoteInDTO);
            }
        }
    }

    private void updateScoreAndSaveVote(int vote, VoteQuoteInDTO voteQuoteInDTO) {
        quoteRepository.updateScore(vote, LocalDateTime.now(),voteQuoteInDTO.getQuoteId());
        voteQuoteRepository.saveAndFlush(VoteQuoteMapper.dtoToVoteQuote(voteQuoteInDTO));
    }

}
