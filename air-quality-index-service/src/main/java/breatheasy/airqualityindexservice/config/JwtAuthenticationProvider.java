package breatheasy.airqualityindexservice.config;

import breatheasy.airqualityindexservice.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        try {
            jwtService.isTokenValid(authenticationToken.getToken());
            AuthenticatedUser user = getAuthenticatedUser(jwtService.extractAllClaims(authenticationToken.getToken()));
            authenticationToken = new JwtAuthenticationToken(user, authenticationToken.getToken());
            return authenticationToken;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AuthenticatedUser getAuthenticatedUser(Claims claims) {
        String firstName = claims.get("FIRST_NAME", String.class);
        String lastName = claims.get("LAST_NAME", String.class);
        List<String> roles = claims.get("ROLES", List.class);
        return AuthenticatedUser.builder()
                .firstName(firstName)
                .lastName(lastName)
                .role(roles)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
