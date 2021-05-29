package pl.sda.springsecurityjwtjavalub25.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByUsername(String username);
    void deleteByUsername(String username);
}
