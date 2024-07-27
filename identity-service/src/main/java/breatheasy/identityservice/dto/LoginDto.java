package breatheasy.identityservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class LoginDto {
    private String username;
    private String password;
}
