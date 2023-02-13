package com.kameleoon.dto.voteQuote;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteQuoteFullDTO {
    private Long userId;

    private Long quoteId;

    private boolean vote;

    private LocalDateTime createdAt;
}
