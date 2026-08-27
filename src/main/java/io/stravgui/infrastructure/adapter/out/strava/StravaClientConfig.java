package io.stravgui.infrastructure.adapter.out.strava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class StravaClientConfig {

    @Bean
    public StravaOAuthClient stravaOAuthClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://www.strava.com/oauth/token")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(StravaOAuthClient.class);
    }

    @Bean
    public StravaActivityClient stravaActivityClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://www.strava.com/api/v3")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(StravaActivityClient.class);
    }
}