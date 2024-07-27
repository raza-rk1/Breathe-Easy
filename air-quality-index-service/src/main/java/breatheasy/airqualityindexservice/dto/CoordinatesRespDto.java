package breatheasy.airqualityindexservice.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoordinatesRespDto {
    private Double lat;
    private Double lon;
    private String name;
    private String state;
    private String country;
    private Map<String, String> local_names;
}
