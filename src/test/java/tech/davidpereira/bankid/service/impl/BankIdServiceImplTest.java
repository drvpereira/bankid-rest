package tech.davidpereira.bankid.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tech.davidpereira.bankid.model.AuthRequest;
import tech.davidpereira.bankid.model.AuthResponse;
import tech.davidpereira.bankid.model.CollectRequest;
import tech.davidpereira.bankid.model.CollectResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BankIdServiceImplTest {

    @Mock
    private RestTemplate mockRt;

    @InjectMocks
    private BankIdServiceImpl bankIdServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAuth() {
        // Setup
        final AuthRequest authRequest = new AuthRequest();
        final AuthResponse expectedResult = new AuthResponse();
        when(mockRt.postForObject(eq("url"), eq(new HttpEntity<>(null)), eq(AuthResponse.class), any(Object.class))).thenReturn(new AuthResponse());

        // Run the test
        final AuthResponse result = bankIdServiceImplUnderTest.auth(authRequest);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAuth_RestTemplateThrowsRestClientException() {
        // Setup
        final AuthRequest authRequest = new AuthRequest();
        final AuthResponse expectedResult = new AuthResponse();
        when(mockRt.postForObject(eq("url"), eq(new HttpEntity<>(null)), eq(AuthResponse.class), any(Object.class))).thenThrow(RestClientException.class);

        // Run the test
        final AuthResponse result = bankIdServiceImplUnderTest.auth(authRequest);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollect() {
        // Setup
        final CollectRequest orderRef = new CollectRequest();
        final CollectResponse expectedResult = new CollectResponse();
        when(mockRt.postForObject(eq("url"), eq(new HttpEntity<>(null)), eq(CollectResponse.class), any(Object.class))).thenReturn(new CollectResponse());

        // Run the test
        final CollectResponse result = bankIdServiceImplUnderTest.collect(orderRef);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollect_RestTemplateThrowsRestClientException() {
        // Setup
        final CollectRequest orderRef = new CollectRequest();
        final CollectResponse expectedResult = new CollectResponse();
        when(mockRt.postForObject(eq("url"), eq(new HttpEntity<>(null)), eq(CollectResponse.class), any(Object.class))).thenThrow(RestClientException.class);

        // Run the test
        final CollectResponse result = bankIdServiceImplUnderTest.collect(orderRef);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
