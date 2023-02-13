package com.kameleoon.mapper;

import com.kameleoon.dto.quote.QuoteFullDTO;
import com.kameleoon.dto.quote.QuoteInDTO;
import com.kameleoon.dto.quote.QuoteOutDTO;
import com.kameleoon.dto.user.UserInDTO;
import com.kameleoon.dto.user.UserOutDTO;
import com.kameleoon.entity.Quote;
import com.kameleoon.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteMapper {

    public static QuoteOutDTO quoteToDto(Quote quote) {
        return QuoteOutDTO.builder()
                .id(quote.getId())
                .text(quote.getText())
                .build();
    }

    public static QuoteFullDTO quoteToFullDto(Quote quote) {
        return QuoteFullDTO.builder()
                .id(quote.getId())
                .userName(quote.getUser().getName())
                .number(quote.getScore())
                .text(quote.getText())
                .build();
    }

    public static Quote dtoToQuote(QuoteInDTO quoteInDto) {
        return Quote.builder()
                .text(quoteInDto.getText())
                .build();
    }

    public static List<QuoteFullDTO> eventToListOutDto(List<Quote> quotes) {
        return quotes.stream().map(QuoteMapper::quoteToFullDto).collect(Collectors.toList());
    }
}
