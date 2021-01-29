package interview.client.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunction.ofRequestProcessor;
import static org.springframework.web.reactive.function.client.WebClient.builder;
import static reactor.core.publisher.Mono.just;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubClientBeanConfiguration {

    protected final static String GIT_HUB_CLIENT_BEAN = "gitHubWebClient";

    private final GitHubConfiguration gitHubConfiguration;

    @Bean(GIT_HUB_CLIENT_BEAN)
    public WebClient client() {
        return builder().baseUrl(gitHubConfiguration.getApiUrl()).filter(logRequest()).build();
    }

    private static ExchangeFilterFunction logRequest() {
        return ofRequestProcessor(clientRequest -> {
            log.trace("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values
                .forEach(value -> log.debug("header name: '{}'; header value'{}'", name, value)));
            return just(clientRequest);
        });
    }
}
