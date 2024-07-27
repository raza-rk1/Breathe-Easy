package breatheasy.airqualityindexservice.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticatedUser {
    private String firstName;
    private String lastName;
    private List<String> role;
}
