package com.sodexo.bankid.service.impl;

import com.sodexo.bankid.model.AuthRequest;
import com.sodexo.bankid.model.AuthResponse;
import com.sodexo.bankid.model.CollectRequest;
import com.sodexo.bankid.model.CollectResponse;
import com.sodexo.bankid.service.BankIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankIdServiceImpl implements BankIdService {

    @Value("${bankid.url}")
    private String bankIdUrl;

    @Autowired
    private RestTemplate rt;

    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);

        return rt.postForObject(bankIdUrl + "auth", request, AuthResponse.class);
    }

    @Override
    public CollectResponse collect(CollectRequest orderRef) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CollectRequest> request = new HttpEntity<>(orderRef, headers);

        return rt.postForObject(bankIdUrl + "collect", request, CollectResponse.class);
    }

}
