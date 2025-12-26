package com.muller.racha_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Racha API", version = "1.0", description = "API para administrar o pagamento de um valor em grupo"))
@SecuritySchemes({
		@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER),
		@SecurityScheme(name = "google", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth", tokenUrl = "https://oauth2.googleapis.com/token", scopes = {
				@OAuthScope(name = "openid"),
				@OAuthScope(name = "profile"),
				@OAuthScope(name = "email")
		}

		))) })
public class RachaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RachaApiApplication.class, args);
	}

}
