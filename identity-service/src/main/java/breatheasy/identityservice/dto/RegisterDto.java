package breatheasy.identityservice.dto;

import breatheasy.identityservice.enums.Role;
import breatheasy.identityservice.enums.UserStatus;
import breatheasy.identityservice.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String countryCode;
    private String mobileNumber;
    private String username;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;
    private List<AddressDto> addresses;
}
