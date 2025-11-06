package org.aquariux.cryptotrade.common;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class RestTemplateApi {

    private final RestTemplate restTemplate;

    public  <T> T callApi(String url, ParameterizedTypeReference<T> typeRef, HttpMethod method) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    typeRef
            );
            return response.getBody();
        } catch (Exception ex) {
            System.out.println("API call error: " + url + " -> " + ex.getMessage());
            return null;
        }
    }

    @Async
    public <T> CompletableFuture<T> callApiAsync(
            String url,
            ParameterizedTypeReference<T> typeRef,
            HttpMethod method
    ) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    typeRef
            );
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception ex) {
            System.out.println("API error: " + url + " => " + ex.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }
}
