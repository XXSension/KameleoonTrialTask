package com.kameleoon.dto.quote;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteOutDTO {

    @NotNull
    private Long id;

    private String text;
}
