package breatheasy.identityservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JwtTokenConfigProperties {
    private Signing signing = new Signing();
    private long expirationTime;
    private long refreshTokenExpirationTime;

    @Data
    public static class Signing {
        private String key;
    }
}

