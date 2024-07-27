package breatheasy.identityservice.service;

import breatheasy.identityservice.dto.JwtAuthenticationResponse;
import breatheasy.identityservice.dto.LoginDto;
import breatheasy.identityservice.dto.RegisterDto;

public interface AuthenticationService {
    JwtAuthenticationResponse registration(RegisterDto request);

    JwtAuthenticationResponse login(LoginDto request);
}
