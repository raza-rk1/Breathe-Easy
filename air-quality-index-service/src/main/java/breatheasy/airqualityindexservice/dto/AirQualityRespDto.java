package breatheasy.airqualityindexservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityRespDto {
    private String name;
    private String state;
    private String country;
    private int airQualityIndex;
    private String airQualityText;
    private Components components;
    private String date;
    private CoordinatesRespDto coordinates;

}
