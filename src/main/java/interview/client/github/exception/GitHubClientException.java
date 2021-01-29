package interview.client.github.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GitHubClientException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GitHubClientException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
