package com.bretanac93.joboard.events;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MoneyCreditedEvent {
    private final UUID accountId;
    private final BigDecimal creditAmount;
}
