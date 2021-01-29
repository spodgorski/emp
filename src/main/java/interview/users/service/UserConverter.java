package interview.users.service;

import interview.users.model.api.User;
import interview.users.model.dto.GitHubUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static org.springframework.beans.BeanUtils.copyProperties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserConverter {

    protected static User convert(final GitHubUser gitHubUser) {
        val user = new User();
        copyProperties(gitHubUser, user);
        user.setCalculations(calculate(gitHubUser.getFollowers(), gitHubUser.getPublicRepositories()));
        return user;
    }

    private static double calculate(final int followers, final int publicRepos) {
        return followers == 0 ? 0 : (6d / followers * (2 + publicRepos));
    }
}
