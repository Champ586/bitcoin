package com.iliushchenko.bitcoin.controller;

import com.iliushchenko.bitcoin.BitcoinApplication;
import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BitcoinApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BitcoinControllerTest {

    private static final DateTimeFormatter PARSER = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void fullTest() {
        sendBalance(1000, "2020-06-13T13:20:05+07:00");
        sendBalance(1.2, "2020-06-14T10:03:00+07:00");
        sendBalance(2.5, "2020-06-14T10:20:40+07:00");
        sendBalance(2.3, "2020-06-14T11:48:00+07:00");
        sendBalance(4,   "2020-06-14T14:10:00+09:00");
        sendBalance(5.5, "2020-06-14T14:59:59+07:00");
        sendBalance(6.5, "2020-06-14T16:03:23+07:00");

        ResponseEntity<BalanceDTO[]> balanceResponse = getBalance("2020-06-14T10:10:00+07:00", "2020-06-14T15:15:23+07:00");
        assertNotNull(balanceResponse.getBody());

        BalanceDTO[] balanceList = balanceResponse.getBody();
        assertEquals(5, balanceList.length);
        assertEquals(0, balanceList[0].getAmount().compareTo(BigDecimal.valueOf(1003.70)));
        assertEquals(0, balanceList[1].getAmount().compareTo(BigDecimal.valueOf(1006.00)));
        assertEquals(0, balanceList[2].getAmount().compareTo(BigDecimal.valueOf(1010.00)));
        assertEquals(0, balanceList[3].getAmount().compareTo(BigDecimal.valueOf(1010.00)));
        assertEquals(0, balanceList[4].getAmount().compareTo(BigDecimal.valueOf(1015.50)));
    }

    private void sendBalance(double amount, String datetime) {
        BalanceDTO balanceDTO = BalanceDTO
                .builder()
                .amount(BigDecimal.valueOf(amount))
                .datetime(ZonedDateTime.parse(datetime, PARSER))
                .build();

        ResponseEntity<BalanceDTO> responseEntity = testRestTemplate.postForEntity("/bitcoins", balanceDTO, BalanceDTO.class, new Object());
    }

    private ResponseEntity<BalanceDTO[]> getBalance(String startDateTime, String endDateTime) {
        DateTimeParams dateTimeParams = DateTimeParams
                .builder()
                .startDatetime(ZonedDateTime.parse(startDateTime, PARSER))
                .endDatetime(ZonedDateTime.parse(endDateTime, PARSER))
                .build();

        ResponseEntity<BalanceDTO[]> responseEntity = testRestTemplate.postForEntity("/bitcoins/balance", dateTimeParams, BalanceDTO[].class, new Object());
        return responseEntity;
    }
}