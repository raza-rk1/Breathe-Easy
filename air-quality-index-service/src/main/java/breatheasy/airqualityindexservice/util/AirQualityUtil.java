package breatheasy.airqualityindexservice.util;

import java.util.HashMap;
import java.util.Map;

public class AirQualityUtil {
    private static final Map<Integer, String> airqualityScaleMap;

    static {
        airqualityScaleMap = new HashMap<>();
        airqualityScaleMap.put(1, "Good");
        airqualityScaleMap.put(2, "Fair");
        airqualityScaleMap.put(3, "Moderate");
        airqualityScaleMap.put(4, "Poor");
        airqualityScaleMap.put(5, "Very Poor");
    }

    public static String getAirQualityByIndex(int index) {
        return airqualityScaleMap.get(index);
    }
}
