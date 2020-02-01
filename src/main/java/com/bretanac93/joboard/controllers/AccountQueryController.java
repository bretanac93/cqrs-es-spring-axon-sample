package com.bretanac93.joboard.controllers;

import com.bretanac93.joboard.entities.BankAccount;
import com.bretanac93.joboard.services.AccountQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Bank account queries")
@AllArgsConstructor
public class AccountQueryController {
    private final AccountQueryService service;

    @GetMapping(value = "/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CompletableFuture<BankAccount> findById(@PathVariable(name = "accountId") String accountId) {
        return this.service.findById(accountId);
    }
}
