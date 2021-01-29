package interview.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.client.github.GitHubClient;
import interview.client.github.exception.GitHubClientException;
import interview.client.github.exception.GitHubClientNotFoundException;
import interview.users.service.UserService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class UserControllerIT {

    private static final String PATH = "/users/{login}";

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GitHubClient gitHubClient;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnNotFoundForNotExistingUser() throws Exception {
        //given
        final String notExistingLogin = "NOT_EXISTING";
        when(gitHubClient.getByLogin(notExistingLogin))
            .thenThrow(new GitHubClientNotFoundException("not found", NOT_FOUND));

        //when
        val result = mockMvc.perform(get(PATH, notExistingLogin)).andExpect(status().isNotFound()).andReturn();

        //then
        assertError(result);
    }

    @Test
    public void shouldHandleClientError() throws Exception {
        //given
        final String login = "login";
        when(gitHubClient.getByLogin(login)).thenThrow(new GitHubClientException("bad request", BAD_REQUEST));

        //when
        val result = mockMvc.perform(get(PATH, login)).andExpect(status().isBadRequest()).andReturn();

        //then
        assertError(result);
    }

    @Test
    public void shouldHandleServiceError() throws Exception {
        final String login = "login";
        when(gitHubClient.getByLogin(login)).thenThrow(new GitHubClientException("service error", SERVICE_UNAVAILABLE));

        //when
        val result = mockMvc.perform(get(PATH, login)).andExpect(status().isServiceUnavailable()).andReturn();

        //then
        assertError(result);
    }

    private void assertError(final MvcResult result) throws Exception {
        assertThat(result).isNotNull();
        assertThat(result.getResponse()).isNotNull();
        val error = objectMapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
    }
}