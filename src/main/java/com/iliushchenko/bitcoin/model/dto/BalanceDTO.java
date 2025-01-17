package com.iliushchenko.bitcoin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceDTO {
    private ZonedDateTime datetime;
    private BigDecimal amount;
}
