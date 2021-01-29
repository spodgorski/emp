package interview.users.service;

import interview.client.github.GitHubClient;
import interview.client.github.exception.GitHubClientNotFoundException;
import interview.users.model.api.User;
import interview.users.model.entity.RequestCountEntity;
import interview.users.repository.RequestCountRepository;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static interview.users.TestUtils.assertConvertedUser;
import static interview.users.TestUtils.getGitHubUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class UserServiceIT {

    private final static String LOGIN = "login";

    @Autowired
    private RequestCountRepository requestCountRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private GitHubClient gitHubClient;

    @BeforeEach
    public void setUp() {
        cleanDatabase();
    }

    @AfterEach
    public void teardown() {
        cleanDatabase();
    }

    private void cleanDatabase() {
        requestCountRepository.deleteAll();
    }

    @Test
    public void shouldAddRequestCountForRequestedLogin() {
        //given
        val githubUser = getGitHubUser(6);
        when(gitHubClient.getByLogin(LOGIN)).thenReturn(githubUser);

        //when
        final User user = userService.getUser(LOGIN);

        //then
        assertUserResponse(1);

        assertConvertedUser(githubUser, user, 4);
    }

    @Test
    public void shouldUpdateRequestCountForRequestedLogin() {
        //given
        val githubUser = getGitHubUser(6);
        when(gitHubClient.getByLogin(LOGIN)).thenReturn(githubUser);
        userService.getUser(LOGIN);

        //when
        final User user = userService.getUser(LOGIN);

        //then
        assertUserResponse(2);

        assertConvertedUser(githubUser, user, 4);
    }

    private void assertUserResponse(final int expectedRequestCount) {
        final List<RequestCountEntity> all = requestCountRepository.findAll();
        assertThat(all).hasSize(1);

        final RequestCountEntity requestCountEntity = all.get(0);
        assertThat(requestCountEntity).isNotNull();
        assertThat(requestCountEntity.getLogin()).isEqualTo(LOGIN);
        assertThat(requestCountEntity.getRequestCount()).isEqualTo(expectedRequestCount);
    }
}