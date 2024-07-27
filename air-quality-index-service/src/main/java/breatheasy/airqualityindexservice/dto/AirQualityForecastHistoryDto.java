package breatheasy.airqualityindexservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityForecastHistoryDto {
    private String date;
    private int airQualityIndex;
    private String airQualityText;
    private Components components;
}
