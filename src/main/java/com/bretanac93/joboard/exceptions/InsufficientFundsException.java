package com.bretanac93.joboard.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(UUID accountId, BigDecimal debitAmount) {
        super("Insufficient Balance: Cannot debit " + debitAmount +
                " from account number [" + accountId.toString() + "]");
    }
}
