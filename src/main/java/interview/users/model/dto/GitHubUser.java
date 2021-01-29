package interview.users.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubUser {

    @JsonProperty("id")
    private int id;

    @JsonProperty("login")
    private String login;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty(value = "avatar_url")
    private String avatarUrl;

    @JsonProperty(value = "created_at")
    private ZonedDateTime createdAt;

    @JsonProperty(value = "public_repos")
    private int publicRepositories;

    @JsonProperty("followers")
    private int followers;
}
