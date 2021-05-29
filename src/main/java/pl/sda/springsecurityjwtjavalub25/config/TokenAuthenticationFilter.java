package pl.sda.springsecurityjwtjavalub25.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.sda.springsecurityjwtjavalub25.api.model.LoginRq;
import pl.sda.springsecurityjwtjavalub25.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            LoginRq loginRq = objectMapper.readValue(request.getInputStream(), LoginRq.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRq.getUsername(), loginRq.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Error in auth process " + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();

        TokenSubject subject = userRepository.findByUsername(username)
                .map(ent -> new TokenSubject(ent.getUsername(), ent.getRole()))
                .orElseThrow(() -> new IllegalStateException("User with given username not exists"));

        String token = JWT.create()
                .withSubject(objectMapper.writeValueAsString(subject))
                .withExpiresAt(new Date(System.currentTimeMillis() + 3_600_000))
                .sign(Algorithm.HMAC512("Javalub25".getBytes()));
        //Wybierz jedno miejsce gdzie będziesz chciał mieć token
        //1
        response.setHeader("TOKEN", token);
        //2
        response.getOutputStream().write(token.getBytes());
    }
}
