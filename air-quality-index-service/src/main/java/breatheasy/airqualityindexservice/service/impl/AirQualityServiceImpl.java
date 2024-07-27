package breatheasy.airqualityindexservice.service.impl;

import breatheasy.airqualityindexservice.dto.*;
import breatheasy.airqualityindexservice.exception.DataNotFoundException;
import breatheasy.airqualityindexservice.exception.InvalidStartEndDateException;
import breatheasy.airqualityindexservice.feignclient.AirQualityApiService;
import breatheasy.airqualityindexservice.service.AirQualityService;
import breatheasy.airqualityindexservice.service.CoordinatesService;
import breatheasy.airqualityindexservice.util.AirQualityUtil;
import breatheasy.airqualityindexservice.util.DateUtil;
import feign.FeignException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AirQualityServiceImpl implements AirQualityService {

    private static final String API_KEY = "aa812867c28472227342bc84a85fc7d2";

    private final CoordinatesService coordinatesService;
    private final AirQualityApiService airQualityApiService;

    public AirQualityServiceImpl(CoordinatesService coordinatesService, AirQualityApiService airQualityApiService) {
        this.coordinatesService = coordinatesService;
        this.airQualityApiService = airQualityApiService;
    }

    @Override
    public AirQualityRespDto getAirQualityIndex(AirQualityRequestDto airQualityRequestDto) {
        log.info("Getting Air Quality Index form external API");
        log.info("Request Data : {}", airQualityRequestDto);
        CoordinatesRespDto coordinates = coordinatesService.getCoordinatesBasedOnCityCountryStateName(airQualityRequestDto);
        try {
            ResponseEntity<AirQualityResponse> airQualityDetailsResponseEntity = airQualityApiService.getAirQualityDetailsByLatLon(coordinates.getLat(), coordinates.getLon(), API_KEY);
            if (HttpStatus.OK.equals(airQualityDetailsResponseEntity.getStatusCode())) {
                AirQualityResponse airQualityResponse = airQualityDetailsResponseEntity.getBody();
                if (airQualityResponse != null && airQualityResponse.getList().size() > 0) {
                    AirQualityEntry airQualityEntry = airQualityResponse.getList().get(0);
                    Components componentsRes = airQualityEntry.getComponents();
                    Main main = airQualityEntry.getMain();
                    Components components = Components.builder()
                            .co(componentsRes.getCo())
                            .no(componentsRes.getNo())
                            .no2(componentsRes.getNo2())
                            .o3(componentsRes.getO3())
                            .so2(componentsRes.getSo2())
                            .pm2_5(componentsRes.getPm2_5())
                            .pm10(componentsRes.getPm10())
                            .nh3(componentsRes.getNh3())
                            .build();
                    return AirQualityRespDto.builder()
                            .country(airQualityRequestDto.getCountry())
                            .state(airQualityRequestDto.getState())
                            .name(airQualityRequestDto.getName())
                            .airQualityIndex(main.getAqi())
                            .airQualityText(AirQualityUtil.getAirQualityByIndex(main.getAqi()))
                            .coordinates(coordinates)
                            .components(components)
                            .date(DateUtil.getLocalDateTime(airQualityEntry.getDt()))
                            .build();
                }else{
                    String errorMessage = "Getting empty response from Air quality Index API.";
                    log.error(errorMessage);
                    throw new InvalidStartEndDateException(errorMessage);
                }
            }else{
                log.error("Getting error response from API . Error {}", airQualityDetailsResponseEntity.getStatusCode());
                throw new InternalServerErrorException("Getting error response from AirQuality API . Error " + airQualityDetailsResponseEntity.getStatusCode());
            }
        } catch (FeignException.FeignClientException exception) {
            log.error("Failed to fetch air quality. Error {}", exception.getMessage());
            throw new InternalServerErrorException("Failed to fetch air quality. Error " + exception.getMessage());
        }
    }

    @Override
    public AirQualityForecastHistoryRespDto getAirQualityForecast(AirQualityRequestDto airQualityRequestDto) {
        CoordinatesRespDto coordinates = coordinatesService.getCoordinatesBasedOnCityCountryStateName(airQualityRequestDto);
        try {
            ResponseEntity<AirQualityResponse> airQualityForecastDetailsByLatLon = airQualityApiService.getAirQualityForecastDetailsByLatLon(coordinates.getLat(), coordinates.getLon(), API_KEY);
            if (HttpStatus.OK.equals(airQualityForecastDetailsByLatLon.getStatusCode())) {
                AirQualityResponse airQualityResponse = airQualityForecastDetailsByLatLon.getBody();
                if (airQualityResponse != null && !airQualityResponse.getList().isEmpty()) {
                    List<AirQualityForecastHistoryDto> airQualityForecastDtoList = new ArrayList<>();
                    AirQualityForecastHistoryRespDto airQualityForecastRespDto = AirQualityForecastHistoryRespDto.builder()
                            .name(coordinates.getName())
                            .state(coordinates.getState())
                            .country(coordinates.getCountry())
                            .airQualityResponseList(airQualityForecastDtoList)
                            .coordinates(coordinates)
                            .build();
                    for(AirQualityEntry airQualityEntry : airQualityResponse.getList()){
                        Components componentsRes = airQualityEntry.getComponents();
                        Main main = airQualityEntry.getMain();
                        Components components = Components.builder()
                                .co(componentsRes.getCo())
                                .no(componentsRes.getNo())
                                .no2(componentsRes.getNo2())
                                .o3(componentsRes.getO3())
                                .so2(componentsRes.getSo2())
                                .pm2_5(componentsRes.getPm2_5())
                                .pm10(componentsRes.getPm10())
                                .nh3(componentsRes.getNh3())
                                .build();
                        AirQualityForecastHistoryDto airQualityForecastDto = AirQualityForecastHistoryDto.builder()
                                .date(DateUtil.getLocalDateTime(airQualityEntry.getDt()))
                                .airQualityIndex(main.getAqi())
                                .airQualityText(AirQualityUtil.getAirQualityByIndex(main.getAqi()))
                                .components(components)
                                .build();
                        airQualityForecastDtoList.add(airQualityForecastDto);
                    }
                    return airQualityForecastRespDto;
                }else{
                    String errorMessage = "Getting empty response from API.";
                    log.error(errorMessage);
                    throw new DataNotFoundException(errorMessage);
                }
            }else{
                log.error("Getting error response from API . Error {}", airQualityForecastDetailsByLatLon.getStatusCode());
                throw new InternalServerErrorException("Getting error response from AirQuality API . Error " + airQualityForecastDetailsByLatLon.getStatusCode());
            }

        }catch (FeignException.FeignClientException exception) {
            log.error("Failed to fetch air quality forecast. Error {}", exception.getMessage());
            throw new InternalServerErrorException("Failed to fetch air quality forecast. Error " + exception.getMessage());
        }
    }

    @Override
    public AirQualityForecastHistoryRespDto getAirQualityHistory(AirQualityHistoryRequestDto airQualityHistoryRequestDto) {
        validateHistoryRequestDto(airQualityHistoryRequestDto);
        AirQualityRequestDto airQualityRequestDto = AirQualityRequestDto.builder()
                .name(airQualityHistoryRequestDto.getName())
                .state(airQualityHistoryRequestDto.getState())
                .country(airQualityHistoryRequestDto.getCountry())
                .build();
        CoordinatesRespDto coordinates = coordinatesService.getCoordinatesBasedOnCityCountryStateName(airQualityRequestDto);
        try {
            ResponseEntity<AirQualityResponse> airQualityHistoryDetailsByLatLon = airQualityApiService
                    .getAirQualityHistoryByLatLonAndStartEndDate(coordinates.getLat(), coordinates.getLon(),
                            DateUtil.convertToEpoch(airQualityHistoryRequestDto.getStartDate()),
                            DateUtil.convertToEpoch(airQualityHistoryRequestDto.getEndDate()), API_KEY);
            if (HttpStatus.OK.equals(airQualityHistoryDetailsByLatLon.getStatusCode())) {
                AirQualityResponse airQualityResponse = airQualityHistoryDetailsByLatLon.getBody();
                if (airQualityResponse != null && !airQualityResponse.getList().isEmpty()) {
                    List<AirQualityForecastHistoryDto> airQualityForecastDtoList = new ArrayList<>();
                    AirQualityForecastHistoryRespDto airQualityForecastRespDto = AirQualityForecastHistoryRespDto.builder()
                            .name(coordinates.getName())
                            .state(coordinates.getState())
                            .country(coordinates.getCountry())
                            .airQualityResponseList(airQualityForecastDtoList)
                            .coordinates(coordinates)
                            .build();
                    for(AirQualityEntry airQualityEntry : airQualityResponse.getList()){
                        Components componentsRes = airQualityEntry.getComponents();
                        Main main = airQualityEntry.getMain();
                        Components components = Components.builder()
                                .co(componentsRes.getCo())
                                .no(componentsRes.getNo())
                                .no2(componentsRes.getNo2())
                                .o3(componentsRes.getO3())
                                .so2(componentsRes.getSo2())
                                .pm2_5(componentsRes.getPm2_5())
                                .pm10(componentsRes.getPm10())
                                .nh3(componentsRes.getNh3())
                                .build();
                        AirQualityForecastHistoryDto airQualityForecastDto = AirQualityForecastHistoryDto.builder()
                                .date(DateUtil.getLocalDateTime(airQualityEntry.getDt()))
                                .airQualityIndex(main.getAqi())
                                .airQualityText(AirQualityUtil.getAirQualityByIndex(main.getAqi()))
                                .components(components)
                                .build();
                        airQualityForecastDtoList.add(airQualityForecastDto);
                    }
                    return airQualityForecastRespDto;
                }else{
                    String errorMessage = "Getting empty response from API.";
                    log.error(errorMessage);
                    throw new InternalServerErrorException(errorMessage);
                }
            }else{
                log.error("Getting error response from API . Error {}", airQualityHistoryDetailsByLatLon.getStatusCode());
                throw new InternalServerErrorException("Getting error response from AirQuality API . Error " + airQualityHistoryDetailsByLatLon.getStatusCode());
            }

        }catch (FeignException.FeignClientException exception) {
            log.error("Failed to fetch air quality forecast. Error {}", exception.getMessage());
            throw new InternalServerErrorException("Failed to fetch air quality forecast. Error " + exception.getMessage());
        }
    }

    private void validateHistoryRequestDto(AirQualityHistoryRequestDto airQualityHistoryRequestDto) {
        LocalDateTime startDateTime = DateUtil.convertToLocalDate(airQualityHistoryRequestDto.getStartDate());
        LocalDateTime endDateTime = DateUtil.convertToLocalDate(airQualityHistoryRequestDto.getEndDate());
        if (startDateTime.isAfter(endDateTime)){
            throw new InvalidStartEndDateException("Start date can not be after end date");
        }
        if(!endDateTime.isBefore(DateUtil.getCurrentDateTime())){
            throw new InvalidStartEndDateException("End date should be in past date");
        }
    }
}
