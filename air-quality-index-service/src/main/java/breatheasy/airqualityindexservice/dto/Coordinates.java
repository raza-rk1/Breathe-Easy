package breatheasy.airqualityindexservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coordinates {
    private double lon;
    private double lat;
}
