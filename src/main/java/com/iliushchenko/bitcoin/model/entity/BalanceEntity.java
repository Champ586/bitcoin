package com.iliushchenko.bitcoin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private ZonedDateTime datetime;

    private BigDecimal amount;

    public BalanceEntity(ZonedDateTime datetime, BigDecimal amount) {
        this.datetime = datetime;
        this.amount = amount;
    }
}
