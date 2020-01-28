package com.bretanac93.joboard.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountCreation {
    private final BigDecimal initialBalance;
    private final String owner;
}
