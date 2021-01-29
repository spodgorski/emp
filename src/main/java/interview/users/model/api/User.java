package interview.users.model.api;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class User {

    private int id;

    private String login;

    private String name;

    private String type;

    private String avatarUrl;

    private ZonedDateTime createdAt;

    private double calculations;
}
