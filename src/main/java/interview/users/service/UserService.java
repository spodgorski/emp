package interview.users.service;

import interview.client.github.GitHubClient;
import interview.users.model.api.User;
import interview.users.repository.RequestCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import static interview.users.service.UserConverter.convert;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GitHubClient gitHubClient;
    private final RequestCountRepository requestCountRepository;

    public User getUser(final String login) {
        val gitHubUser = gitHubClient.getByLogin(login);
        requestCountRepository.upsertRequestCount(login);
        return convert(gitHubUser);
    }

}
