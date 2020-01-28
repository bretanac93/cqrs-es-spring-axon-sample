package com.bretanac93.joboard.services;

import com.bretanac93.joboard.command.CreateAccountCommand;
import com.bretanac93.joboard.command.CreditMoneyCommand;
import com.bretanac93.joboard.command.DebitMoneyCommand;
import com.bretanac93.joboard.dto.AccountCreation;
import com.bretanac93.joboard.dto.MoneyAmount;
import com.bretanac93.joboard.entities.BankAccount;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class AccountCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<BankAccount> createAccount(AccountCreation dto) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID(),
                dto.getInitialBalance(),
                dto.getOwner()
        ));
    }

    public CompletableFuture<String> creditMoneyToAccount(String id, MoneyAmount dto) {
        return this.commandGateway.send(new CreditMoneyCommand(
                ServiceUtils.formatUuid(id),
                dto.getAmount()
        ));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String id, MoneyAmount dto) {
        return this.commandGateway.send(new DebitMoneyCommand(
                ServiceUtils.formatUuid(id),
                dto.getAmount()
        ));
    }
}
