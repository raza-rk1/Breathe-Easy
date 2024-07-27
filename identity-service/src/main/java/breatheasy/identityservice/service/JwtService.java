package breatheasy.identityservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails, Map<String, Object> claims, String tokenId, long expirationTimeInMillis);

    boolean isTokenValid(String token, UserDetails userDetails);
}
