package com.example.onlineLearning.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthTests {

//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void testHealthEndpointLearningPath() {
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/health/app", String.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).contains("UP");
//
//    }

}
