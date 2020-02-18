package tech.davidpereira.bankid.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import tech.davidpereira.bankid.model.*;
import tech.davidpereira.bankid.service.BankIdService;

import java.nio.charset.Charset;

import static net.glxn.qrgen.javase.QRCode.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BankIdControllerTest {

    @Mock
    private BankIdService mockBankIdService;

    @InjectMocks
    private BankIdController bankIdController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAuth() {
        final AuthRequest authRequest = new AuthRequest(null, "10.10.10.10");
        final ResponseEntity<AuthResponse> expectedResult = ResponseEntity.ok(new AuthResponse("orderRef1", "autoStartToken1"));

        when(mockBankIdService.auth(new AuthRequest(null, "10.10.10.10"))).thenReturn(new AuthResponse("orderRef1", "autoStartToken1"));

        final ResponseEntity<AuthResponse> result = bankIdController.auth(authRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testAuthWithException() {
        final AuthRequest authRequest = new AuthRequest(null, "10.10.10.10");
        final ResponseEntity<AuthResponse> expectedResult = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        when(mockBankIdService.auth(new AuthRequest(null, "10.10.10.10"))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        final ResponseEntity<AuthResponse> result = bankIdController.auth(authRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testQrCode() {
        final String autoStartToken = "testAutoStartToken";
        final ResponseEntity<byte[]> expectedResult = ResponseEntity.ok(from(autoStartToken).stream().toByteArray());

        final ResponseEntity<byte[]> result = bankIdController.qrCode(autoStartToken);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollect() {
        final CollectRequest collectRequest = new CollectRequest("orderRef1");
        final ResponseEntity<CollectResponse> expectedResult = ResponseEntity.ok(new CollectResponse("status1", "hintCode1", new CompletionData()));
        when(mockBankIdService.collect(new CollectRequest("orderRef1"))).thenReturn(new CollectResponse("status1", "hintCode1", new CompletionData()));

        final ResponseEntity<CollectResponse> result = bankIdController.collect(collectRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollectWithGeneralException() {
        final CollectRequest collectRequest = new CollectRequest("orderRef1");
        final ResponseEntity<CollectResponse> expectedResult = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(mockBankIdService.collect(new CollectRequest("orderRef1"))).thenThrow(RuntimeException.class);

        final ResponseEntity<CollectResponse> result = bankIdController.collect(collectRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollectWithHttpClientException() {
        final CollectRequest collectRequest = new CollectRequest("orderRef1");
        final ResponseEntity<CollectResponse> expectedResult = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(mockBankIdService.collect(new CollectRequest("orderRef1"))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        final ResponseEntity<CollectResponse> result = bankIdController.collect(collectRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCollectWithNoSuchOrderException() {
        final CollectRequest collectRequest = new CollectRequest("orderRef1");
        final ResponseEntity<CollectResponse> expectedResult = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        final HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "",
                "[{\"errorCode\":\"invalidParameters\",\"details\":\"No such order\"}]".getBytes(), Charset.defaultCharset());

        when(mockBankIdService.collect(new CollectRequest("orderRef1"))).thenThrow(exception);

        final ResponseEntity<CollectResponse> result = bankIdController.collect(collectRequest);

        assertEquals(expectedResult, result);
    }

}
