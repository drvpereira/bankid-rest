# bankid-rest
A Swedish Mobile BankID authentication client

### Generating the certificates


The first thing that is needed to set up BankID authentication is to get two certificates: 
a RP (Relying Party) Certificate and a Server Certificate. The RP Certificate is provided 
by the bank which we purchased the BankID service from, but for testing purposes we can 
get a test certificate from the [BankID website](https://www.bankid.com/bankid-i-dina-tjanster/rp-info). 
The Server Certificate can be taken from the [BankID Relying Party Guidelines](https://www.bankid.com/assets/bankid/rp/bankid-relying-party-guidelines-v3.2.2.pdf). 
In order to communicate with the BankID's core service, you need to add the Server Certificate 
to a Java's trust store and the RP Certificate to a Java key store.

To import the Server Certificate to a truststore, we need to do the following:

` keytool -importcert -file BankId.cer -keystore truststore.jks -alias "BankId"`

To import the RP Certificate to a keystore, we need to do the following:

` keytool -importkeystore -srckeystore FPTestcert2_20150818_102329.pfx -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype JKS`

These certificates have already been added to a truststore/keystore and are available in the resources folder of this project.
 
### Run:
To start the service run:  
` mvn spring-boot:run`

Then open the Swagger UI by going to the following URL:  
` http://localhost:8080/swagger-ui.html`

### Endpoints


* `/auth` starts the authentication process.
* `/collect` retrieves the authentication status by sending the `orderRef` number gotten from the `/auth` response.
* `/qrcode/{autoStartToken}` generates a QR Code based on the `autoStartToken` gotten from the `/auth` response. The QR code can be read by the Mobile BankID app to perform the authentication without the need of informing the social security number in a desktop page or app, for example.  