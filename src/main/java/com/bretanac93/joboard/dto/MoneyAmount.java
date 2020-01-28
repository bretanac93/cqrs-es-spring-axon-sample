package com.bretanac93.joboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MoneyAmount {
    private BigDecimal amount;
}
