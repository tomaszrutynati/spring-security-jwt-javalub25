package pl.sda.springsecurityjwtjavalub25.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenSubject {
    private String username;
    private String role;

    //.. tu wrzucacie kolejne dane, ktore chcecie szyfrowac w tokenie
}
