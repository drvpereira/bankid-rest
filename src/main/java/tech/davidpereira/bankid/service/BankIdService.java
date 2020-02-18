package com.sodexo.bankid.service;

import com.sodexo.bankid.model.AuthRequest;
import com.sodexo.bankid.model.AuthResponse;
import com.sodexo.bankid.model.CollectRequest;
import com.sodexo.bankid.model.CollectResponse;

public interface BankIdService {

    AuthResponse auth(AuthRequest authRequest);

    CollectResponse collect(CollectRequest orderRef);

}
