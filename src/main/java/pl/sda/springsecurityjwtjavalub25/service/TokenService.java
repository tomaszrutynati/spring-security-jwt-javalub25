package pl.sda.springsecurityjwtjavalub25.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.springsecurityjwtjavalub25.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void logout(String username) {
        tokenRepository.deleteByUsername(username);
    }
}
