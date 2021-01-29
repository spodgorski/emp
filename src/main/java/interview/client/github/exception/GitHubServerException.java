package interview.client.github.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GitHubServerException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GitHubServerException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
