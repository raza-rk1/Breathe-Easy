package breatheasy.airqualityindexservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirQualityHistoryRequestDto {
    private String country;
    private String state;
    private String name;
    private String startDate;
    private String endDate;

}
