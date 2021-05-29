package pl.sda.springsecurityjwtjavalub25.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sda.springsecurityjwtjavalub25.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(ent -> new User(ent.getUsername(), ent.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + ent.getRole()))))
                .orElseThrow(() -> new UsernameNotFoundException("User not exists"));

    }
}
