package com.bretanac93.joboard.projections;

import com.bretanac93.joboard.entities.BankAccount;
import com.bretanac93.joboard.events.AccountCreatedEvent;
import com.bretanac93.joboard.events.MoneyCreditedEvent;
import com.bretanac93.joboard.events.MoneyDebitedEvent;
import com.bretanac93.joboard.exceptions.AccountNotFoundException;
import com.bretanac93.joboard.queries.FindBankAccountQuery;
import com.bretanac93.joboard.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountProjection {
    private final BankAccountRepository repository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("Handling a bank account creation command {}", event.getId());
        BankAccount bankAccount = new BankAccount(event.getId(), event.getOwner(), event.getInitialBalance());
        this.repository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling an account credit command {}", event.getAccountId());
        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getAccountId());
        if (!optionalBankAccount.isPresent()) {
            throw new AccountNotFoundException(event.getAccountId());
        }
        BankAccount bankAccount = optionalBankAccount.get();
        bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditAmount()));
        this.repository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling an account credit command {}", event.getAccountId());
        Optional<BankAccount> optionalBankAccount = this.repository.findById(event.getAccountId());
        if (!optionalBankAccount.isPresent()) {
            throw new AccountNotFoundException(event.getAccountId());
        }
        BankAccount bankAccount = optionalBankAccount.get();
        bankAccount.setBalance(bankAccount.getBalance().add(event.getDebitAmount()));
        this.repository.save(bankAccount);
    }

    @QueryHandler
    public BankAccount handle(FindBankAccountQuery query) {
        log.debug("Handling FindBankAccountQuery: {}", query);
        return this.repository.findById(query.getAccountId()).orElse(null);
    }
}
