package com.kameleoon.service;

import com.kameleoon.dto.quote.QuoteFullDTO;
import com.kameleoon.dto.quote.QuoteInDTO;
import com.kameleoon.dto.quote.QuoteOutDTO;
import com.kameleoon.entity.Quote;
import com.kameleoon.entity.SortType;
import com.kameleoon.entity.User;
import com.kameleoon.exception.ConflictException;
import com.kameleoon.exception.NotFoundException;
import com.kameleoon.mapper.QuoteMapper;
import com.kameleoon.repository.QuoteRepository;
import com.kameleoon.repository.UserRepository;
import com.kameleoon.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import static com.kameleoon.exception.ExceptionDescriptions.QUOTE_ALREADY_EXISTS;
import static com.kameleoon.exception.ExceptionDescriptions.QUOTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    private final UserRepository userRepository;
    private final VoteQuoteRepository voteQuoteRepository;

    public QuoteOutDTO createQuote(QuoteInDTO quoteInDTO) {
        User user = getUserFromRepository(quoteInDTO.getUserId());
        Quote quote = QuoteMapper.dtoToQuote(quoteInDTO);
        quote.setUser(user);
        try {
            return QuoteMapper.quoteToDto(quoteRepository.save(quote));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(QUOTE_ALREADY_EXISTS.getTitle());
        }
    }

    public QuoteOutDTO updateQoute(Long id, QuoteInDTO quoteInDTO) {
        Quote quote = getQuoteFromRepositoryByUserId(id, quoteInDTO.getUserId());
        return QuoteMapper.quoteToDto(quoteRepository.saveAndFlush(quote));
    }

    public QuoteOutDTO getQoute(Long id) {
        return QuoteMapper.quoteToDto(quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle())));
    }

    @Transactional(readOnly = true)
    public List<QuoteFullDTO> getTopQuote(SortType sortType) {
        List<Quote> quotes = sortType == SortType.TOP ? quoteRepository.findTop10ByOrderByScoreAsc()
                : quoteRepository.findTop10ByOrderByScoreDesc();
        return QuoteMapper.eventToListOutDto(quotes);

    }

    @Transactional(readOnly = true)
    public QuoteOutDTO getRandomQuote() throws NoSuchAlgorithmException {
        List<Quote> quotes = quoteRepository.findAll();
        int random = SecureRandom.getInstanceStrong().nextInt(quotes.size());
        return QuoteMapper.quoteToDto(quotes.get(random));
    }

    public void deleteQuote(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        voteQuoteRepository.deleteByQuote(quote);
        quoteRepository.delete(quote);
    }

    private User getUserFromRepository(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }

    private Quote getQuoteFromRepositoryByUserId(Long quoteId, Long userId) {
        return quoteRepository.findByIdAndUserId(quoteId,userId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }
}
