package interview.users;

import interview.users.model.api.User;
import interview.users.model.dto.GitHubUser;
import lombok.val;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public final class TestUtils {

    public static GitHubUser getGitHubUser(final int followers) {
        val githubUser = new GitHubUser();
        githubUser.setId(1);
        githubUser.setLogin("login");
        githubUser.setName("name");
        githubUser.setType("type");
        githubUser.setAvatarUrl("http://avatar");
        githubUser.setCreatedAt(now(UTC));
        githubUser.setFollowers(followers);
        githubUser.setPublicRepositories(2);
        return githubUser;
    }

    public static void assertConvertedUser(final GitHubUser githubUser, final User convertedUser,
                                           final int expectedCalculations) {
        assertThat(convertedUser).isNotNull();
        assertThat(convertedUser.getId()).isEqualTo(githubUser.getId());
        assertThat(convertedUser.getLogin()).isEqualTo(githubUser.getLogin());
        assertThat(convertedUser.getName()).isEqualTo(githubUser.getName());
        assertThat(convertedUser.getType()).isEqualTo(githubUser.getType());
        assertThat(convertedUser.getAvatarUrl()).isEqualTo(githubUser.getAvatarUrl());
        assertThat(convertedUser.getCreatedAt()).isEqualTo(githubUser.getCreatedAt());
        assertThat(convertedUser.getCalculations()).isEqualTo(expectedCalculations);
    }
}