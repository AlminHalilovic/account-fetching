package com.example.k8s.accountfetching.service;

import java.util.Arrays;
import java.util.List;

import com.example.k8s.accountfetching.domain.UserSettingsAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserSettingsService {
    private final static RestTemplate restTemplateClient = new RestTemplate();

    @Value("${api.user-settings.url}")
    private String baseUrl;

    public List<UserSettingsAccount> getUserSettingsAccounts() {

        String url = String.format("%s/settings/account-settings", baseUrl);
        ResponseEntity<UserSettingsAccount[]> response =
                restTemplateClient.getForEntity(
                        url,
                        UserSettingsAccount[].class);
        UserSettingsAccount[] accounts = response.getBody();

        return Arrays.asList(accounts.clone());
    }
}
