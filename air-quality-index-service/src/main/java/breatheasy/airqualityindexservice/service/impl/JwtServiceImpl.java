package breatheasy.airqualityindexservice.service.impl;

import breatheasy.airqualityindexservice.config.JwtTokenConfigProperties;
import breatheasy.airqualityindexservice.constant.ErrorMessage;
import breatheasy.airqualityindexservice.exception.TokenValidationException;
import breatheasy.airqualityindexservice.service.JwtService;
import breatheasy.airqualityindexservice.util.UUIDEncryptionUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtTokenConfigProperties jwtTokenConfigProperties;

    @Override
    public boolean isTokenValid(String token) throws Exception {
        try {
            final String userName = UUIDEncryptionUtil.decryptUUID(extractUserName(token));
            return (userName != null && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            log.warn(ErrorMessage.TOKE_EXPIRED);
            throw new TokenValidationException(ErrorMessage.TOKE_EXPIRED);
        } catch (MalformedJwtException e) {
            log.warn(ErrorMessage.TOKE_MALFORMED);
            throw new TokenValidationException(ErrorMessage.TOKE_MALFORMED);
        } catch (SignatureException e) {
            log.warn(ErrorMessage.TOKE_IS_INVALID);
            throw new TokenValidationException(ErrorMessage.TOKE_IS_INVALID);
        } catch (Exception e) {
            log.warn(ErrorMessage.TOKE_VALIDATION_FAILED);
            throw new TokenValidationException(ErrorMessage.TOKE_VALIDATION_FAILED);
        }
    }

    private String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtTokenConfigProperties.getSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
