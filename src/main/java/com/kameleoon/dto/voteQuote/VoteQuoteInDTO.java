package com.kameleoon.dto.voteQuote;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuoteInDTO {

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long quoteId;

    private boolean vote;
}
