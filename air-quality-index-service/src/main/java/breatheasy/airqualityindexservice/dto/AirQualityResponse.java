package breatheasy.airqualityindexservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityResponse {
    private Coordinates coord;
    private List<AirQualityEntry> list;
}

