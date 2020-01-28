package com.bretanac93.joboard.controllers;

import com.bretanac93.joboard.dto.AccountCreation;
import com.bretanac93.joboard.dto.MoneyAmount;
import com.bretanac93.joboard.entities.BankAccount;
import com.bretanac93.joboard.services.AccountCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Bank account commands")
@AllArgsConstructor
public class AccountCommandController {
    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<BankAccount> createAccount(@RequestBody AccountCreation dto) {
        return this.accountCommandService.createAccount(dto);
    }

    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyAmount dto) {
        return this.accountCommandService.creditMoneyToAccount(accountId, dto);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyAmount dto) {
        return this.accountCommandService.debitMoneyFromAccount(accountId, dto);
    }
}
