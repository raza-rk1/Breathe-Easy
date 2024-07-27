package breatheasy.airqualityindexservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityRequestDto {
    private String country;
    private String state;
    private String name;

}
