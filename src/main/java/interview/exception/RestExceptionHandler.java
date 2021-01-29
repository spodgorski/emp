package interview.exception;

import interview.client.github.exception.GitHubClientException;
import interview.client.github.exception.GitHubClientNotFoundException;
import interview.client.github.exception.GitHubServerException;
import java.net.ConnectException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GitHubClientException.class)
    protected ResponseEntity<Error> handleGitHubClientException(final GitHubClientException ex) {
        return getError(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler(value = GitHubClientNotFoundException.class)
    protected ResponseEntity<Error> handleGitHubClientNotFoundException(final GitHubClientNotFoundException ex) {
        return getError(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler(value = GitHubServerException.class)
    protected ResponseEntity<Error> handleGitHubServerException(final GitHubServerException ex) {
        return getError(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler(value = ConnectException.class)
    protected ResponseEntity<Error> handleConnectException(final ConnectException ex) {
        return getError(HttpStatus.I_AM_A_TEAPOT, "Github service unavailable");
    }

    private ResponseEntity<Error> getError(final HttpStatus status, final String message) {
        val error = Error.builder().code(status.value()).message(message).build();
        return new ResponseEntity<>(error, status);
    }
}
