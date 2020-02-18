package tech.davidpereira.bankid.service;


import tech.davidpereira.bankid.model.AuthRequest;
import tech.davidpereira.bankid.model.AuthResponse;
import tech.davidpereira.bankid.model.CollectRequest;
import tech.davidpereira.bankid.model.CollectResponse;

public interface BankIdService {

    AuthResponse auth(AuthRequest authRequest);

    CollectResponse collect(CollectRequest orderRef);

}
