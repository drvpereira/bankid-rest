package com.sodexo.bankid.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cert {

    private LocalDateTime notBefore;

    private LocalDateTime notAfter;

}