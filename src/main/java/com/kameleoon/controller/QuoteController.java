package com.kameleoon.controller;


import com.kameleoon.dto.quote.QuoteFullDTO;
import com.kameleoon.dto.quote.QuoteInDTO;
import com.kameleoon.dto.quote.QuoteOutDTO;
import com.kameleoon.entity.SortType;
import com.kameleoon.service.QuoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.kameleoon.exception.ExceptionDescriptions.UNKNOWN_TYPE_OF_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping
    public QuoteOutDTO createQuote(@Valid @RequestBody QuoteInDTO quoteInDTO) {
        return quoteService.createQuote(quoteInDTO);
    }

    @GetMapping("/{id}")
    public QuoteOutDTO get(@NotNull @Positive @PathVariable long id) {
        return quoteService.getQoute(id);
    }

    @GetMapping("/top10")
    public List<QuoteFullDTO> getTop10Quote(@RequestParam(defaultValue = "TOP") String order) {
        SortType sortType = SortType.from(order)
                .orElseThrow(() -> new IllegalArgumentException(UNKNOWN_TYPE_OF_SORT.getTitle()));
        return quoteService.getTopQuote(sortType);
    }

    @GetMapping(value = "random")
    public QuoteOutDTO getRandomQuote() throws NoSuchAlgorithmException {
        return quoteService.getRandomQuote();
    }

    @PutMapping("/{id}")
    public QuoteOutDTO updateQuote(@NotNull @Positive @PathVariable Long id,
                              @Valid @RequestBody QuoteInDTO quoteInDto) {
        return quoteService.updateQoute(id, quoteInDto);
    }

    @DeleteMapping("/{id}")
    public void deleteQuote(@NotNull @Positive @PathVariable Long id) {
        quoteService.deleteQuote(id);
    }
}
