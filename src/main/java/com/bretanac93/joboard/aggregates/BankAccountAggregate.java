package com.bretanac93.joboard.aggregates;

import com.bretanac93.joboard.command.CreateAccountCommand;
import com.bretanac93.joboard.command.CreditMoneyCommand;
import com.bretanac93.joboard.command.DebitMoneyCommand;
import com.bretanac93.joboard.events.AccountCreatedEvent;
import com.bretanac93.joboard.events.MoneyCreditedEvent;
import com.bretanac93.joboard.events.MoneyDebitedEvent;
import com.bretanac93.joboard.exceptions.InsufficientFundsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class BankAccountAggregate {
    @AggregateIdentifier
    private UUID id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getAccountId(),
                command.getInitialBalance(),
                command.getOwner()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.id = event.getId();
        this.balance = event.getInitialBalance();
        this.owner = event.getOwner();
    }

    @CommandHandler
    public void handle(CreditMoneyCommand command) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(
                command.getAccountId(),
                command.getCreditAmount()
        ));
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(
                command.getAccountId(),
                command.getDebitAmount()
        ));
    }

    @EventSourcingHandler
    public void on(MoneyDebitedEvent event) throws InsufficientFundsException {
        if (this.balance.compareTo(event.getDebitAmount()) < 0) {
            throw new InsufficientFundsException(event.getAccountId(), event.getDebitAmount());
        }
        this.balance = this.balance.subtract(event.getDebitAmount());
    }
}
