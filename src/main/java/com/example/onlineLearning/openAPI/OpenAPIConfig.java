package com.example.onlineLearning.openAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

//    http://localhost:8080/swagger-ui.html
//    http://localhost:8080/v3/api-docs
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Online Learning"));
    }





}
