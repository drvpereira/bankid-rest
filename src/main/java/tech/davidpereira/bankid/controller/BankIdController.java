package com.sodexo.bankid.controller;

import com.sodexo.bankid.model.AuthRequest;
import com.sodexo.bankid.model.AuthResponse;
import com.sodexo.bankid.model.CollectRequest;
import com.sodexo.bankid.model.CollectResponse;
import com.sodexo.bankid.service.BankIdService;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
public class BankIdController {

    @Autowired
    private BankIdService bankIdAdapter;

    @PostMapping(value = "/auth",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(bankIdAdapter.auth(authRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/qrcode/{autoStartToken}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> qrCode(@PathVariable("autoStartToken") String autoStartToken) {
        ByteArrayOutputStream stream = QRCode.from(autoStartToken).stream();
        return ResponseEntity.ok(stream.toByteArray());
    }

    @PostMapping(value = "/collect",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectResponse> collect(@RequestBody CollectRequest collectRequest) {
        try {
            return ResponseEntity.ok(bankIdAdapter.collect(collectRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
