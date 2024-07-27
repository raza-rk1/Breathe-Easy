package breatheasy.airqualityindexservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityEntry {
    private Main main;
    private Components components;
    private long dt;
}
