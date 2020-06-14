package com.iliushchenko.bitcoin.builder;

import com.iliushchenko.bitcoin.model.dto.BalanceDTO;
import com.iliushchenko.bitcoin.model.entity.BalanceEntity;

public class BalanceBuilder {

    public static BalanceEntity buildEntityFromDTO(BalanceDTO dto) {
        if (dto == null)
            return null;

        return BalanceEntity
                .builder()
                .amount(dto.getAmount())
                .datetime(dto.getDatetime())
                .build();
    }

    public static BalanceDTO buildDTOFromEntity(BalanceEntity entity) {
        if (entity == null)
            return null;

        return BalanceDTO
                .builder()
                .amount(entity.getAmount())
                .datetime(entity.getDatetime())
                .build();
    }
}
