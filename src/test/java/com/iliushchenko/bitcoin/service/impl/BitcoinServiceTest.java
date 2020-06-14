package com.iliushchenko.bitcoin.service.impl;

import com.iliushchenko.bitcoin.BitcoinApplication;
import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;
import com.iliushchenko.bitcoin.model.entity.BalanceEntity;
import com.iliushchenko.bitcoin.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BitcoinApplication.class)
class BitcoinServiceTest {

    @MockBean
    private BalanceRepository balanceRepository;

    @Autowired
    private BitcoinService bitcoinService;

    @BeforeEach
    void setUp() {
        LocalDateTime ldt1 = LocalDateTime.of(2020, Month.JUNE, 14, 11, 0);
        LocalDateTime ldt2 = LocalDateTime.of(2020, Month.JUNE, 14, 12, 15);
        LocalDateTime ldt3 = LocalDateTime.of(2020, Month.JUNE, 14, 14, 30);

        List<BalanceEntity> balanceEntityList = List.of(
                new BalanceEntity(1L, ldt1.atZone(ZoneId.of("Asia/Bangkok")), BigDecimal.valueOf(10)),
                new BalanceEntity(2L, ldt1.atZone(ZoneId.of("Asia/Bangkok")), BigDecimal.valueOf(20)),
                new BalanceEntity(3L, ldt2.atZone(ZoneId.of("Asia/Bangkok")), BigDecimal.valueOf(30)),
                new BalanceEntity(4L, ldt3.atZone(ZoneId.of("Asia/Bangkok")), BigDecimal.valueOf(40)),
                new BalanceEntity(5L, ldt3.atZone(ZoneId.of("Asia/Bangkok")), BigDecimal.valueOf(50))
        );
        when(balanceRepository.findBalanceEntitiesByDatetimeBetweenOrderByDatetime(any(), any()))
                .thenReturn(balanceEntityList);

        when(balanceRepository.getBalanceBeforeDate(any()))
                .thenReturn(java.util.Optional.ofNullable(BigDecimal.valueOf(1000)));
    }

    @Test
    void putMoney() {
    }

    @Test
    void getListBetweenDates() {
    }

    @Test
    void getListBetweenDatesGroupedByHour() {
        LocalDateTime ldt1 = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        LocalDateTime ldt2 = LocalDateTime.of(2020, Month.JUNE, 14, 16, 15);
        DateTimeParams dateTimeParams = new DateTimeParams(ldt1.atZone(ZoneId.of("Asia/Bangkok")), ldt2.atZone(ZoneId.of("Asia/Bangkok")));
        List<BalanceDTO> res = bitcoinService.getListBetweenDatesGroupedByHour(dateTimeParams);

        assertEquals(6, res.size());
        assertEquals(BigDecimal.valueOf(1030), res.get(0).getAmount());
        assertEquals(BigDecimal.valueOf(1030), res.get(1).getAmount());
        assertEquals(BigDecimal.valueOf(1060), res.get(2).getAmount());
        assertEquals(BigDecimal.valueOf(1060), res.get(3).getAmount());
        assertEquals(BigDecimal.valueOf(1150), res.get(4).getAmount());
        assertEquals(BigDecimal.valueOf(1150), res.get(5).getAmount());
    }
}