package co.vistafoundation.vlearning.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SecurityScheme(name = "Authorization", description = "JWT auth description", 
                 scheme = "bearer",
                 type = SecuritySchemeType.HTTP, 
                 bearerFormat = "JWT",
                 in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(

		security = { @SecurityRequirement(name = "Authorization") },

		servers = {
				         @Server(description = "local", url = "http://localhost:9090"),
				         @Server(description = "dev", url = "https://dev-api.v-learning.in"),
				         @Server(description = "test", url = "https://test-api.v-learning.in"),
				         @Server(description = "preprod", url = "https://preprod-api.v-learning.in")

		})

public class OpenApiConfig {

}
