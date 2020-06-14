package com.iliushchenko.bitcoin.validator;

import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;
import com.iliushchenko.bitcoin.model.dto.ErrorDTO;

import java.math.BigDecimal;

public class ValidationHelper {

    public static ErrorDTO validateBalanceDTO(BalanceDTO balanceDTO) {
        if (balanceDTO == null)
            return new ErrorDTO("Request body is null");

        if (balanceDTO.getDatetime() == null)
            return new ErrorDTO( "Datetime is null");

        if (balanceDTO.getAmount() == null)
            return new ErrorDTO( "Amount is null");

        if (balanceDTO.getAmount().compareTo(BigDecimal.ZERO) < 0)
            return new ErrorDTO( "Amount is less than zero");

        return null;
    }

    public static ErrorDTO validateDateTimeParams(DateTimeParams dateTimeParams) {
        if (dateTimeParams == null)
            return new ErrorDTO("Request body is null");

        if (dateTimeParams.getStartDatetime() == null)
            return new ErrorDTO("StartDatetime is null");

        if (dateTimeParams.getEndDatetime() == null)
            return new ErrorDTO("EndDatetime is null");

        if (dateTimeParams.getEndDatetime().isBefore(dateTimeParams.getStartDatetime()))
            return new ErrorDTO("StartDatetime is before EndDatetime");

        return null;
    }

}
