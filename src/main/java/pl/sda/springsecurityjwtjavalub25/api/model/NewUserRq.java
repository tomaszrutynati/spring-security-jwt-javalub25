package pl.sda.springsecurityjwtjavalub25.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserRq {
    private String username;
    private String password;
    private String role;
}
