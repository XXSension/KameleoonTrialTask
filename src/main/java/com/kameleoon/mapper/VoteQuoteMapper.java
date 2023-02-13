package com.kameleoon.mapper;

import com.kameleoon.dto.voteQuote.VoteQuoteInDTO;
import com.kameleoon.entity.VoteQuote;

public class VoteQuoteMapper {

    public static Integer voteQuoteToInteger(VoteQuote voteQuote) {
        return voteQuote.getScoreAfterVote();
    }

    public static VoteQuote dtoToVoteQuote(VoteQuoteInDTO voteQuoteInDTO) {
        return VoteQuote.builder()
                .vote(voteQuoteInDTO.isVote())
                .build();
    }
}
