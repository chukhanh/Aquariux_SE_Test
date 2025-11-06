package org.aquariux.cryptotrade.config;


import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value(value = "${api.max.total}")
    private int maxTotal;

    @Value(value = "${api.per.route}")
    private int perRoute;

    @Value(value = "${api.connect.request.timeout}")
    private int connectRequestTimeout;

    @Value(value = "${api.connect.timeout}")
    private int connectTimeout;

    @Value(value = "${api.read.timeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(perRoute);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(TimeValue.ofSeconds(30))
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        requestFactory.setConnectTimeout(connectRequestTimeout);
        requestFactory.setReadTimeout(connectTimeout);
        requestFactory.setConnectionRequestTimeout(readTimeout);

        return new RestTemplate(requestFactory);
    }
}
