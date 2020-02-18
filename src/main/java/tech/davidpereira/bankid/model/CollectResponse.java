package com.sodexo.bankid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectResponse {

  private String status;

  private String hintCode;

  private CompletionData completionData;

}
