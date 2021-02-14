package com.example.k8s.accountfetching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AccountFetchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountFetchingApplication.class, args);
    }

}
