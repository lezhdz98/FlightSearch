package com.encora.flightsearchBT.service;

import com.encora.flightsearchBT.models.AmadeusAuthorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenService("testClientId", "testClientSecret", "https://test.amadeus.com", restTemplate);
    }

    @Test
    public void testRequestToken() {
        AmadeusAuthorization mockResponse = new AmadeusAuthorization();
        mockResponse.setAccessToken("testAccessToken");

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        AmadeusAuthorization result = tokenService.requestToken();

        assertNotNull(result);
        assertEquals("testAccessToken", result.getAccessToken());
    }
}