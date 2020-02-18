package com.sodexo.bankid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

import static org.springframework.util.ResourceUtils.getFile;

@SpringBootApplication
public class BankidRestApplication {

	@Value("${bankid.password}")
	private String password;

	@Bean
	public RestTemplate rt() throws Exception {
		SSLContext sslContext = SSLContextBuilder
				.create()
				.loadTrustMaterial(getFile("classpath:truststore.jks"), password.toCharArray())
				.loadKeyMaterial(getFile("classpath:keystore.jks"), password.toCharArray(), password.toCharArray())
				.build();

		HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));

	}

	public static void main(String[] args) {
		SpringApplication.run(BankidRestApplication.class, args);
	}

}
