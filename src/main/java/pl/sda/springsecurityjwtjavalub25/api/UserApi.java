package pl.sda.springsecurityjwtjavalub25.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.sda.springsecurityjwtjavalub25.api.model.NewUserRq;
import pl.sda.springsecurityjwtjavalub25.service.TokenService;
import pl.sda.springsecurityjwtjavalub25.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping
    public void registerUser(@RequestBody NewUserRq rq) {
        userService.registerUser(rq);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public void logoutUser() {

        String user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().toString();

        tokenService.logout(user);
    }
}
