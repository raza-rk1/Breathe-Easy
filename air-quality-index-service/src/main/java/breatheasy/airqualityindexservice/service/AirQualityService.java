package breatheasy.airqualityindexservice.service;

import breatheasy.airqualityindexservice.dto.AirQualityForecastHistoryRespDto;
import breatheasy.airqualityindexservice.dto.AirQualityHistoryRequestDto;
import breatheasy.airqualityindexservice.dto.AirQualityRequestDto;
import breatheasy.airqualityindexservice.dto.AirQualityRespDto;


public interface AirQualityService {

    AirQualityRespDto getAirQualityIndex(AirQualityRequestDto airQualityRequestDto);

    AirQualityForecastHistoryRespDto getAirQualityForecast(AirQualityRequestDto airQualityRequestDto);

    AirQualityForecastHistoryRespDto getAirQualityHistory(AirQualityHistoryRequestDto airQualityHistoryRequestDto);





}
