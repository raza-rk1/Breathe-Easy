package breatheasy.airqualityindexservice.feignclient;

import breatheasy.airqualityindexservice.dto.AirQualityResponse;
import breatheasy.airqualityindexservice.dto.CoordinatesRespDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "airQualityApiService", url = "http://api.openweathermap.org")
public interface AirQualityApiService {

//    http://api.openweathermap.org/geo/1.0/direct?q=London,GB,England&limit=1&appid=aa812867c28472227342bc84a85fc7d2

    @GetMapping("/geo/1.0/direct")
    ResponseEntity<List<CoordinatesRespDto>> getCoordinatesByCountryStateCityName(
            @RequestParam("q") String countryStateCityName,
            @RequestParam("limit") int limit,
            @RequestParam("appid") String apiKey
    );

    //    http://api.openweathermap.org/data/2.5/air_pollution?lat=50&lon=50&appid=aa812867c28472227342bc84a85fc7d2
    @GetMapping("/data/2.5/air_pollution")
    ResponseEntity<AirQualityResponse> getAirQualityDetailsByLatLon(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("appid") String apiKey
    );

    //http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=50&lon=50&appid=aa812867c28472227342bc84a85fc7d2
    @GetMapping("/data/2.5/air_pollution/forecast")
    ResponseEntity<AirQualityResponse> getAirQualityForecastDetailsByLatLon(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("appid") String apiKey
    );

//    http://api.openweathermap.org/data/2.5/air_pollution/history?lat=50&lon=50&start=1606223802&end=1606482999&appid=aa812867c28472227342bc84a85fc7d2

    @GetMapping("/data/2.5/air_pollution/history")
    ResponseEntity<AirQualityResponse> getAirQualityHistoryByLatLonAndStartEndDate(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("start") long start,
            @RequestParam("end") long end,
            @RequestParam("appid") String apiKey
    );



}
