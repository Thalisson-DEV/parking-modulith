package com.example.carparking.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Car Parking API",
                version = "1.0",
                description = "Parking management api for commercial establishment",
                license = @License(name = "MIT")
        )
)
public class OpenApiConfig {
}
