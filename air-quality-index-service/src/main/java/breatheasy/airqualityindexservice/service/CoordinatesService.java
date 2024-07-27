package breatheasy.airqualityindexservice.service;

import breatheasy.airqualityindexservice.dto.AirQualityRequestDto;
import breatheasy.airqualityindexservice.dto.CoordinatesRespDto;

public interface CoordinatesService {

    CoordinatesRespDto getCoordinatesBasedOnCityCountryStateName(AirQualityRequestDto airQualityRequestDto);

}
