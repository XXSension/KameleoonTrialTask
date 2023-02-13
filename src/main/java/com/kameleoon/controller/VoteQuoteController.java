package com.kameleoon.controller;

import com.kameleoon.dto.voteQuote.VoteQuoteInDTO;
import com.kameleoon.repository.QuoteRepository;
import com.kameleoon.service.VoteQuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/quotes")
@RequiredArgsConstructor
public class VoteQuoteController {

    private final VoteQuoteService voteQuoteService;

    @GetMapping("{quoteId}/score-history")
    public List<Integer> getScoreHistory(@PathVariable long quoteId) {
        return voteQuoteService.getScoresHistory(quoteId);
    }

    @PostMapping("/votes")
    public void vote(@Valid @RequestBody VoteQuoteInDTO voteQuoteInDto) {
        voteQuoteService.vote(voteQuoteInDto);
    }
}
