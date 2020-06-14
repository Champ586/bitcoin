package com.iliushchenko.bitcoin.controller;

import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.dto.DateTimeParams;
import com.iliushchenko.bitcoin.model.dto.ErrorDTO;
import com.iliushchenko.bitcoin.service.BalanceService;
import com.iliushchenko.bitcoin.validator.ValidationHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BitcoinController {

    private BalanceService balanceService;

    public BitcoinController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/bitcoins")
    public ResponseEntity putBitcoins(@RequestBody BalanceDTO balanceChange) {

        ErrorDTO validationError = ValidationHelper.validateBalanceDTO(balanceChange);
        if (validationError != null)
            return ResponseEntity.badRequest().body(validationError);

        balanceService.putMoney(balanceChange);

        return ResponseEntity.ok(balanceChange);
    }

    @PostMapping("/bitcoins/balance")
    public ResponseEntity getBalanceOf(@RequestBody DateTimeParams params) {

        ErrorDTO validationError = ValidationHelper.validateDateTimeParams(params);
        if (validationError != null)
            return ResponseEntity.badRequest().body(validationError);

        List<BalanceDTO> balanceDTOList = balanceService.getListBetweenDatesGroupedByHour(params);
        return ResponseEntity.ok(balanceDTOList);
    }

}
