package breatheasy.identityservice.service.impl;

import breatheasy.identityservice.config.JwtTokenConfigProperties;
import breatheasy.identityservice.dto.JwtAuthenticationResponse;
import breatheasy.identityservice.dto.LoginDto;
import breatheasy.identityservice.dto.RegisterDto;
import breatheasy.identityservice.enums.Role;
import breatheasy.identityservice.mapper.UserMapper;
import breatheasy.identityservice.model.User;
import breatheasy.identityservice.repository.UserRepository;
import breatheasy.identityservice.service.AuthenticationService;
import breatheasy.identityservice.service.JwtService;
import breatheasy.identityservice.util.UUIDEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtTokenConfigProperties jwtTokenConfigProperties;

    @Override
    public JwtAuthenticationResponse registration(RegisterDto registerDto) {
        User user = userMapper.registerDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        String tokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();

        var accessToken = jwtService.generateToken(user, getClaims(user, tokenId, false),
                tokenId,jwtTokenConfigProperties.getExpirationTime() );
        var refreshToken = jwtService.generateToken(user, getClaims(user, refreshTokenId, true),
                refreshTokenId, jwtTokenConfigProperties.getRefreshTokenExpirationTime());
        return JwtAuthenticationResponse.builder()
                .token(accessToken)
                .tokenId(tokenId)
                .refreshToken(refreshToken)
                .refreshTokenId(refreshTokenId)
                .build();
    }

    @Override
    public JwtAuthenticationResponse login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String tokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();
        var accessToken = jwtService.generateToken(user, getClaims(user, tokenId, false),
                tokenId,jwtTokenConfigProperties.getExpirationTime() );
        var refreshToken = jwtService.generateToken(user, getClaims(user, refreshTokenId, true),
                refreshTokenId, jwtTokenConfigProperties.getRefreshTokenExpirationTime());
        return JwtAuthenticationResponse.builder()
                .token(accessToken)
                .tokenId(tokenId)
                .refreshToken(refreshToken)
                .refreshTokenId(refreshTokenId)
                .build();
    }

    private Map<String, Object> getClaims(User user, String tokenId, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("FIRST_NAME", user.getFirstName());
        claims.put("LAST_NAME", user.getLastName());
        claims.put("ROLE", List.of(user.getRole()));
        var tokenKey = isRefreshToken?"REFRESH_TOKEN_ID":"ACCESS_TOKEN_ID";
        claims.put(tokenKey, tokenId);
        return claims;
    }
}


