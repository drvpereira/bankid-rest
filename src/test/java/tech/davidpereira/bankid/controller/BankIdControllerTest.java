package tech.davidpereira.bankid.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import tech.davidpereira.bankid.model.AuthRequest;
import tech.davidpereira.bankid.model.AuthResponse;
import tech.davidpereira.bankid.model.CollectRequest;
import tech.davidpereira.bankid.model.CollectResponse;
import tech.davidpereira.bankid.service.BankIdService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BankIdControllerTest {

    @Mock
    private BankIdService mockBankIdAdapter;

    @InjectMocks
    private BankIdController bankIdControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAuth() {
        // Setup
        final AuthRequest authRequest = new AuthRequest();
        final ResponseEntity<AuthResponse> expectedResult = new ResponseEntity<>(null);
        when(mockBankIdAdapter.auth(new AuthRequest())).thenReturn(new AuthResponse());

        // Run the test
        final ResponseEntity<AuthResponse> result = bankIdControllerUnderTest.auth(authRequest);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testQrCode() {
        // Setup
        final String autoStartToken = "autoStartToken";
        final ResponseEntity<byte[]> expectedResult = new ResponseEntity<>(null);

        // Run the test
        final ResponseEntity<byte[]> result = bankIdControllerUnderTest.qrCode(autoStartToken);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollect() {
        // Setup
        final CollectRequest collectRequest = new CollectRequest();
        final ResponseEntity<CollectResponse> expectedResult = new ResponseEntity<>(null);
        when(mockBankIdAdapter.collect(new CollectRequest())).thenReturn(new CollectResponse());

        // Run the test
        final ResponseEntity<CollectResponse> result = bankIdControllerUnderTest.collect(collectRequest);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
