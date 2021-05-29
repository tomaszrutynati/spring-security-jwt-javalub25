package pl.sda.springsecurityjwtjavalub25.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        String subjectJson = JWT.require(Algorithm.HMAC512("Javalub25".getBytes()))
                .build()
                .verify(authHeader.replaceAll("Bearer ", ""))
                .getSubject();
        if (subjectJson != null && !subjectJson.isEmpty()) {
            TokenSubject subject = objectMapper.readValue(subjectJson, TokenSubject.class);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(subject.getUsername(), null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + subject.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
