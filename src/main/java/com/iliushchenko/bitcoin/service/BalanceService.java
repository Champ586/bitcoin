package com.iliushchenko.bitcoin.service;

import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;

import java.util.List;

public interface BalanceService {
    void putMoney(BalanceDTO balanceDTO);

    List<BalanceDTO> getListBetweenDates(DateTimeParams dateTimeParams);

    List<BalanceDTO> getListBetweenDatesGroupedByHour(DateTimeParams dateTimeParams);
}
