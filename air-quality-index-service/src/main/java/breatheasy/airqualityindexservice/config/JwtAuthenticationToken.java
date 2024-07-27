package breatheasy.airqualityindexservice.config;

import breatheasy.airqualityindexservice.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class JwtAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private String token;

    private AuthenticatedUser user;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

    }

    public JwtAuthenticationToken(String token){
        super(Collections.emptyList());
        this.token  = token;
        this.setAuthenticated(true);
    }

    public JwtAuthenticationToken(AuthenticatedUser user, String token){
        super(Collections.emptyList());
        this.user = user;
        this.token  = token;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.isAuthenticated()?this.token: null;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    public String getToken() {
        return token;
    }

    public AuthenticatedUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JwtAuthenticationToken that = (JwtAuthenticationToken) o;
        return Objects.equals(token, that.token) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token, user);
    }
}
