package com.encora.flightsearchBT.service;

import com.encora.flightsearchBT.models.AmadeusAuthorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    private final String clientId;
    private final String clientSecret;
    private final String urlAmadeus;
    private final RestTemplate restTemplate;

    public TokenService(@Value("${amadeus.client_id}") String clientId,
                        @Value("${amadeus.client_secret}") String clientSecret,
                        @Value("${amadeus.url}") String urlAmadeus,
                        RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.urlAmadeus = urlAmadeus;
        this.restTemplate = restTemplate;
    }

    public AmadeusAuthorization requestToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AmadeusAuthorization> response = restTemplate.postForEntity(
                urlAmadeus, request, AmadeusAuthorization.class);

        return response.getBody();
    }
}