package breatheasy.airqualityindexservice.controller;

import breatheasy.airqualityindexservice.dto.AirQualityForecastHistoryRespDto;
import breatheasy.airqualityindexservice.dto.AirQualityHistoryRequestDto;
import breatheasy.airqualityindexservice.dto.AirQualityRequestDto;
import breatheasy.airqualityindexservice.dto.AirQualityRespDto;
import breatheasy.airqualityindexservice.service.AirQualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aqi")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AirQualityIndexController {

    private final AirQualityService airQualityService;

    @PostMapping("/get-air-quality")
    public ResponseEntity<AirQualityRespDto> getAirQuality(@RequestBody AirQualityRequestDto dto) {
        AirQualityRespDto airQualityIndex = airQualityService.getAirQualityIndex(dto);
        return ResponseEntity.ok(airQualityIndex);
    }


    @PostMapping("/get-air-quality-forecast")
    public ResponseEntity<AirQualityForecastHistoryRespDto> getAirQualityForecastByLatLan(@RequestBody AirQualityRequestDto dto) {
        AirQualityForecastHistoryRespDto airQualityForecast = airQualityService.getAirQualityForecast(dto);
        return ResponseEntity.ok(airQualityForecast);
    }

    @PostMapping("/get-air-quality-history")
    public ResponseEntity<AirQualityForecastHistoryRespDto> getAirQualityHistoryByLatLan(@RequestBody AirQualityHistoryRequestDto dto) {
        AirQualityForecastHistoryRespDto airQualityForecast = airQualityService.getAirQualityHistory(dto);
        return ResponseEntity.ok(airQualityForecast);
    }
}
