package tech.davidpereira.bankid.controller;

import net.glxn.qrgen.javase.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import tech.davidpereira.bankid.model.AuthRequest;
import tech.davidpereira.bankid.model.AuthResponse;
import tech.davidpereira.bankid.model.CollectRequest;
import tech.davidpereira.bankid.model.CollectResponse;
import tech.davidpereira.bankid.service.BankIdService;

import java.io.ByteArrayOutputStream;

@RestController
public class BankIdController {

    private static Logger logger = LoggerFactory.getLogger(BankIdController.class);

    @Autowired
    private BankIdService bankIdService;

    @PostMapping(value = "/auth",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        try {
            logger.info("Starting BankID authentication process with request {}", authRequest);
            AuthResponse authResponse = bankIdService.auth(authRequest);
            logger.info("Got authentication response {} for request {}", authResponse, authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            logger.error("Error when trying to authenticate with request " + authRequest + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/qrcode/{autoStartToken}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> qrCode(@PathVariable("autoStartToken") String autoStartToken) {
        logger.info("Creating QR Code for autoStartToken '{}'", autoStartToken);
        ByteArrayOutputStream stream = QRCode.from(autoStartToken).stream();
        return ResponseEntity.ok(stream.toByteArray());
    }

    @PostMapping(value = "/collect",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectResponse> collect(@RequestBody CollectRequest collectRequest) {
        try {
            logger.info("Collecting BankID authentication status for request {}", collectRequest);
            CollectResponse collectResponse = bankIdService.collect(collectRequest);
            logger.info("Got collect response {} for request {}", collectResponse, collectRequest);
            return ResponseEntity.ok(collectResponse);
        } catch (HttpClientErrorException e) {
            logger.error("Error when trying to collect authentication status with request " + collectRequest + ": " + e.getMessage(), e);

            String errorBody = e.getResponseBodyAsString();
            if (errorBody.contains("No such order"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            logger.error("Error when trying to collect authentication status with request " + collectRequest + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
