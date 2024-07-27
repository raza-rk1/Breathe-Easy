package breatheasy.airqualityindexservice.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    boolean isTokenValid(String token) throws Exception;

     Claims extractAllClaims(String token);
}
