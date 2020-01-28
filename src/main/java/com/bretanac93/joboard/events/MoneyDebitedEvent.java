package com.bretanac93.joboard.events;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MoneyDebitedEvent {
    private final UUID accountId;
    private final BigDecimal debitAmount;
}
