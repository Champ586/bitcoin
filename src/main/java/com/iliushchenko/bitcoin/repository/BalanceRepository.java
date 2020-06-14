package com.iliushchenko.bitcoin.repository;

import com.iliushchenko.bitcoin.model.entity.BalanceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends CrudRepository<BalanceEntity, Long> {

    @Query("SELECT SUM(b.amount) FROM BalanceEntity b WHERE b.datetime < ?1")
    Optional<BigDecimal> getBalanceBeforeDate(ZonedDateTime dateTime);

    List<BalanceEntity> findBalanceEntitiesByDatetimeBetweenOrderByDatetime(ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
