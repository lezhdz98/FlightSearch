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

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Service
public class TokenService {

    private final String clientId;
    private final String clientSecret;
    private final String urlAmadeus;
    private final RestTemplate restTemplate;

    public TokenService(@Value("${amadeus.client_id}") String clientId,
                        @Value("${amadeus.client_secret}") String clientSecret,
                        @Value("${amadeus.url}") String urlAmadeus,
                        RestTemplateBuilder restTemplateBuilder) throws NoSuchAlgorithmException, KeyManagementException {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.urlAmadeus = urlAmadeus;
        this.restTemplate = getRestTemplate(restTemplateBuilder);
    }

    private RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        sslContext.init(null, trustManagers, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        return restTemplateBuilder.requestFactory(SimpleClientHttpRequestFactory.class).build();
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

