package protonaut.model;

import io.micronaut.core.annotation.Introspected;
import protonaut.validation.RealUserName;
import protonaut.validation.UniqueUserName;

import javax.validation.constraints.NotBlank;

@Introspected
public class User {
    private Long userId;
    @NotBlank
    @RealUserName
    @UniqueUserName
    private String username;
    @NotBlank
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
