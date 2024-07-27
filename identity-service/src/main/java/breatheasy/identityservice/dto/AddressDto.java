package breatheasy.identityservice.dto;

import breatheasy.identityservice.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AddressDto {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    @Enumerated(EnumType.STRING)
    private AddressType type;
    private Boolean primaryAddress;
}
