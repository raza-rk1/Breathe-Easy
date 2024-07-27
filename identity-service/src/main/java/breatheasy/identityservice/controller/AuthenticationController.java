package breatheasy.identityservice.controller;


import breatheasy.identityservice.dto.JwtAuthenticationResponse;
import breatheasy.identityservice.dto.LoginDto;
import breatheasy.identityservice.dto.RegisterDto;
import breatheasy.identityservice.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private  AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody RegisterDto request) {
        return ResponseEntity.ok(authenticationService.registration(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
