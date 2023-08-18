package com.example.onlineLearning.openAPI;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenAPIConfigTest {

    @Mock
    private OpenAPIConfig openAPIConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        openAPIConfig = new OpenAPIConfig();
    }

    @Test
    public void testCustomOpenAPI() {
        OpenAPI openAPI = openAPIConfig.customOpenAPI();
        Info info = openAPI.getInfo();
        assertEquals("Online Learning", info.getTitle());
        // You can add more assertions based on your expected OpenAPI configuration
    }

}
