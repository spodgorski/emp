package interview.client.github;

import interview.client.github.exception.GitHubClientException;
import interview.client.github.exception.GitHubClientNotFoundException;
import interview.client.github.exception.GitHubServerException;
import interview.users.model.dto.GitHubUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static interview.client.github.GitHubClientBeanConfiguration.GIT_HUB_CLIENT_BEAN;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class GitHubClient {

    private static final String USER_PATH = "/users/{login}";

    private final WebClient gitHubWebClient;

    public GitHubClient(@Qualifier(GIT_HUB_CLIENT_BEAN) final WebClient gitHubWebClient) {
        this.gitHubWebClient = gitHubWebClient;
    }

    public GitHubUser getByLogin(final String login) {
        final String userUri = fromUriString(USER_PATH).buildAndExpand(login).toString();
        return gitHubWebClient.get().uri(userUri).retrieve()
            .onStatus(returnedStatus -> returnedStatus.equals(NOT_FOUND), clientResponse -> {
                final String message = format("User with id '%s' not found.", login);
                throw new GitHubClientNotFoundException(message, clientResponse.statusCode());
            }).onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                throw new GitHubClientException("Client error.", clientResponse.statusCode());
            }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                throw new GitHubServerException("server error.", clientResponse.statusCode());
            }).bodyToMono(GitHubUser.class).block();
    }
}
