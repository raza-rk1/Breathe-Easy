package breatheasy.airqualityindexservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityForecastHistoryRespDto {
    private String name;
    private String state;
    private String country;
    private List<AirQualityForecastHistoryDto> airQualityResponseList;
    private CoordinatesRespDto coordinates;
}
