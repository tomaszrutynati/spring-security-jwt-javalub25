package pl.sda.springsecurityjwtjavalub25.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String onlyForUser() {
        return "methodOnlyForUsers";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String onlyForAdmin() {
        return "methodOnlyForUsers";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logged")
    public String onlyForAuthorized() {
        return "methodOnlyForAuthorized";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public String forAll() {
        return "methodForAll";
    }
}
