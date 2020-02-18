package tech.davidpereira.bankid.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import tech.davidpereira.bankid.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class BankIdServiceImplTest {

    @Mock
    private RestTemplate mockRt;

    @InjectMocks
    private BankIdServiceImpl bankIdService;

    private String bankIdUrl = "https://appapi2.test.bankid.com/rp/v5";

    @BeforeEach
    public void setUp() {
        initMocks(this);
        setField(bankIdService, "bankIdUrl", bankIdUrl);
    }

    @Test
    public void testAuth() {
        final AuthRequest authRequest = new AuthRequest();
        final AuthResponse expectedResult = new AuthResponse("orderRef1", "autoStartToken1");

        when(mockRt.postForObject(eq(bankIdUrl + "/auth"), eq(getHttpEntity(authRequest)), eq(AuthResponse.class)))
                .thenReturn(new AuthResponse("orderRef1", "autoStartToken1"));

        final AuthResponse result = bankIdService.auth(authRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollect() {
        final CollectRequest collectRequest = new CollectRequest();
        final CollectResponse expectedResult = new CollectResponse("status1", "hintCode1", new CompletionData());

        when(mockRt.postForObject(eq(bankIdUrl + "/collect"), eq(getHttpEntity(collectRequest)), eq(CollectResponse.class)))
                .thenReturn(new CollectResponse("status1", "hintCode1", new CompletionData()));

        final CollectResponse result = bankIdService.collect(collectRequest);

        assertEquals(expectedResult, result);
    }

    private <T> HttpEntity<T> getHttpEntity(Object request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(request, headers);
    }

}
