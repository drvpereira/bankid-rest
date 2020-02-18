package tech.davidpereira.bankid.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.davidpereira.bankid.model.AuthRequest;
import tech.davidpereira.bankid.model.AuthResponse;
import tech.davidpereira.bankid.model.CollectRequest;
import tech.davidpereira.bankid.model.CollectResponse;
import tech.davidpereira.bankid.service.BankIdService;

@Service
public class BankIdServiceImpl implements BankIdService {

    private static Logger logger = LoggerFactory.getLogger(BankIdServiceImpl.class);

    @Value("${bankid.url}")
    private String bankIdUrl;

    @Autowired
    private RestTemplate rt;

    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);

        logger.info("Calling {} with request {} and headers {}", bankIdUrl + "/auth", authRequest, headers);
        return rt.postForObject(bankIdUrl + "/auth", request, AuthResponse.class);
    }

    @Override
    public CollectResponse collect(CollectRequest collectRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CollectRequest> request = new HttpEntity<>(collectRequest, headers);

        logger.info("Calling {} with request {} and headers {}", bankIdUrl + "/collect", collectRequest, headers);
        return rt.postForObject(bankIdUrl + "/collect", request, CollectResponse.class);
    }

}
