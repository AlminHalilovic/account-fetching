package com.example.k8s.accountfetching.controller;

import java.util.*;

import com.example.k8s.accountfetching.domain.Account;
import com.example.k8s.accountfetching.domain.UserSettingsAccount;
import com.example.k8s.accountfetching.service.UserSettingsService;
import com.example.k8s.accountfetching.utils.AccountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountFetchingController {

    private Logger log = LogManager.getLogger(this.getClass());


    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private AccountUtil accountUtil;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {

        return ResponseEntity.ok(fetchAccounts());
    }

    private List<Account> fetchAccounts() {

        List<Account> accounts = accountUtil.getAccounts();

        try {

            List<UserSettingsAccount> userSettingsAccountList = userSettingsService.getUserSettingsAccounts();

            log.info("Successfully fetched user settings");
            accounts
                    .forEach(account -> {
                        Optional<UserSettingsAccount> userSettingsAccountOptional = userSettingsAccountList.stream()
                                .filter(x -> Objects.equals(account.getName().toUpperCase(), x.getName().toUpperCase()))
                                .findFirst();

                        if (userSettingsAccountOptional.isPresent()) {
                            account.setOrder(userSettingsAccountOptional.get().getOrder());
                        }
                    });

        } catch (Exception e) {
            log.error("Error fetching user settings", e);
        }
        return accounts;
    }
}
