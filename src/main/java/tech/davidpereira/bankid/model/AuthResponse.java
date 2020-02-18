package com.sodexo.bankid.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String orderRef;

    private String autoStartToken;

}