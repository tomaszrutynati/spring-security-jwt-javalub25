package pl.sda.springsecurityjwtjavalub25.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.sda.springsecurityjwtjavalub25.api.model.NewUserRq;
import pl.sda.springsecurityjwtjavalub25.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody NewUserRq rq) {
        userService.registerUser(rq);
    }
}
