package com.sodexo.bankid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest {

    private String personalNumber;

    private String endUserIp;

}
