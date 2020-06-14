package com.iliushchenko.bitcoin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateTimeParams {
    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
}
