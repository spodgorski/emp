package interview.users.service;

import lombok.val;
import org.junit.jupiter.api.Test;

import static interview.users.TestUtils.assertConvertedUser;
import static interview.users.TestUtils.getGitHubUser;
import static interview.users.service.UserConverter.convert;

class UserConverterTest {

    @Test
    public void shouldConvertGithubUserToApiUserAndCalculate() {
        //given
        val githubUser = getGitHubUser(6);

        //when
        val convertedUser = convert(githubUser);

        //then
        assertConvertedUser(githubUser, convertedUser, 4);
    }

    @Test
    public void shouldReturn0InCalculationsIfFollowersEquals0() {
        //given
        val githubUser = getGitHubUser(0);

        //when
        val convertedUser = convert(githubUser);

        //then
        assertConvertedUser(githubUser, convertedUser, 0);
    }
}