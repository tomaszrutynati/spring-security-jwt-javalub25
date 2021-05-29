package pl.sda.springsecurityjwtjavalub25.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.springsecurityjwtjavalub25.api.model.NewUserRq;
import pl.sda.springsecurityjwtjavalub25.repository.UserEntity;
import pl.sda.springsecurityjwtjavalub25.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void registerUser(NewUserRq rq) {
        if (userRepository.findByUsername(rq.getUsername()).isPresent()) {
            throw new IllegalStateException("User with this name already exists");
        }

        UserEntity entity = UserEntity.builder()
                .username(rq.getUsername())
                .password(passwordEncoder.encode(rq.getPassword()))
                .role(rq.getRole())
                .build();

        userRepository.save(entity);
    }
}
