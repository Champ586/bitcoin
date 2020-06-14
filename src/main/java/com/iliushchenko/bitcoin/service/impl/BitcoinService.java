package com.iliushchenko.bitcoin.service.impl;

import com.iliushchenko.bitcoin.builder.BalanceBuilder;
import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;
import com.iliushchenko.bitcoin.model.entity.BalanceEntity;
import com.iliushchenko.bitcoin.repository.BalanceRepository;
import com.iliushchenko.bitcoin.service.BalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitcoinService implements BalanceService {

    private BalanceRepository balanceRepository;

    public BitcoinService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public void putMoney(BalanceDTO balanceDTO) {

        BalanceEntity entity = BalanceBuilder.buildEntityFromDTO(balanceDTO);
        if (entity == null)
            return;

        balanceRepository.save(entity);
    }

    @Override
    public List<BalanceDTO> getListBetweenDates(DateTimeParams dateTimeParams) {
        List<BalanceEntity> entityList = balanceRepository.findBalanceEntitiesByDatetimeBetweenOrderByDatetime(dateTimeParams.getStartDatetime(), dateTimeParams.getEndDatetime());
        return entityList
                .stream()
                .map(BalanceBuilder::buildDTOFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BalanceDTO> getListBetweenDatesGroupedByHour(DateTimeParams dateTimeParams) {
        List<BalanceDTO> balanceList = getListBetweenDates(dateTimeParams);
        if (balanceList == null)
            balanceList = new ArrayList<>();

        List<BalanceDTO> resultList = new ArrayList<>();
        ZonedDateTime startDatetime = dateTimeParams.getStartDatetime();
        ZonedDateTime hourTime = startDatetime.withNano(0).withSecond(0).withMinute(0).plusHours(1);
        ZonedDateTime endDatetime = dateTimeParams.getEndDatetime();

        // getting the sum of previous operations
        BigDecimal sum = balanceRepository.getBalanceBeforeDate(startDatetime).orElse(BigDecimal.ZERO);

        for (BalanceDTO balanceDTO : balanceList) {
            while (balanceDTO.getDatetime().isAfter(hourTime)) {
                resultList.add(new BalanceDTO(hourTime, sum));
                hourTime = hourTime.plusHours(1);
            }
            sum = sum.add(balanceDTO.getAmount());
        }

        while (hourTime.isBefore(endDatetime)) {
            resultList.add(new BalanceDTO(hourTime, sum));
            hourTime = hourTime.plusHours(1);
        }

        return resultList;
    }

}
