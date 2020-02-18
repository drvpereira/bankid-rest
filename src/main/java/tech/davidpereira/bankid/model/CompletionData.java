package com.sodexo.bankid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionData {

  private User user;

  private Device device;

  private Cert cert;

  private String signature;

  private String ocspResponse;

}
