package com.muller.racha_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Racha API", version = "1.0", description = "API para administrar o pagamento de um valor em grupo"))
public class RachaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RachaApiApplication.class, args);
	}

}
