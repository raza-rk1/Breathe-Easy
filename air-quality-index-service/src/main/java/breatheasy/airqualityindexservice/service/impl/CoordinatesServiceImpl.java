package breatheasy.airqualityindexservice.service.impl;

import breatheasy.airqualityindexservice.dto.AirQualityRequestDto;
import breatheasy.airqualityindexservice.dto.CoordinatesRespDto;
import breatheasy.airqualityindexservice.exception.DataNotFoundException;
import breatheasy.airqualityindexservice.feignclient.AirQualityApiService;
import breatheasy.airqualityindexservice.model.Coordinate;
import breatheasy.airqualityindexservice.repository.CoordinateRepository;
import breatheasy.airqualityindexservice.service.CoordinatesService;
import feign.FeignException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoordinatesServiceImpl implements CoordinatesService {

    private final CoordinateRepository coordinateRepository;
    private final AirQualityApiService airQualityApiService;

    public CoordinatesServiceImpl(CoordinateRepository coordinateRepository, AirQualityApiService airQualityApiService) {
        this.coordinateRepository = coordinateRepository;
        this.airQualityApiService = airQualityApiService;
    }

    private static final String API_KEY = "aa812867c28472227342bc84a85fc7d2";

    @Override
    public CoordinatesRespDto getCoordinatesBasedOnCityCountryStateName(AirQualityRequestDto airQualityRequestDto) {
        try{
            Optional<Coordinate> optionalCoordinate = coordinateRepository.findByCountryAndStateAndName(
                    airQualityRequestDto.getCountry(), airQualityRequestDto.getState(), airQualityRequestDto.getName());
            if (optionalCoordinate.isPresent()) {
                Coordinate coordinates = optionalCoordinate.get();
                return CoordinatesRespDto.builder()
                        .lat(coordinates.getLat())
                        .lon(coordinates.getLon())
                        .name(coordinates.getName())
                        .state(coordinates.getState())
                        .country(coordinates.getCountry())
                        .build();
            }
            String q = airQualityRequestDto.getName() + "," +
                    airQualityRequestDto.getCountry() + "," +
                    airQualityRequestDto.getState();
            ResponseEntity<List<CoordinatesRespDto>> coordinatesRespDtoResponseEntity = airQualityApiService.getCoordinatesByCountryStateCityName(q, 1, API_KEY);
            if(HttpStatus.OK.equals(coordinatesRespDtoResponseEntity.getStatusCode())) {
                List<CoordinatesRespDto> coordinatesRespDtoList = coordinatesRespDtoResponseEntity.getBody();
                if(coordinatesRespDtoList != null && coordinatesRespDtoList.size() > 0) {
                    CoordinatesRespDto coordinatesRespDto = coordinatesRespDtoList.get(0);
                    Coordinate coordinate = Coordinate.builder()
                            .name(coordinatesRespDto.getName())
                            .state(coordinatesRespDto.getState())
                            .country(coordinatesRespDto.getCountry())
                            .lat(coordinatesRespDto.getLat())
                            .lon(coordinatesRespDto.getLon())
                            .build();
                    coordinateRepository.save(coordinate);
                    return coordinatesRespDto;
                }else{
                    String errorMessage = "Coordinate not found with give details: - "+q;
                    log.error("Getting Error Message {}", errorMessage);
                    throw new DataNotFoundException(errorMessage);
                }
            }else{
                String errorMessage = "Getting error response from Air quality Index API";
                log.error(errorMessage);
                log.error("Error code is : {}", coordinatesRespDtoResponseEntity.getStatusCode());
                throw new InternalServerErrorException(errorMessage);
            }
        }catch (FeignException.FeignClientException exception){
            log.error("Getting FeignClientException",exception);
            throw new InternalServerErrorException(exception.getMessage());
        }
        catch (Exception exception){
            log.error("Internal Server Error", exception);
            throw new InternalServerErrorException(exception.getMessage());
        }



    }
}
