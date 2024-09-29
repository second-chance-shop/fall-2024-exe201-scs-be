package scs.exe201.secondchanceshopbe.services.googleService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service

public class GoogleMapsService {
    private String apiKey = "AIzaSyAfGIBkUf5FHUP0cHzUeit0RHcHYXYkx_Q";

    private static final String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric";

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getDistance(String origin, String destination) {
        String url = DISTANCE_MATRIX_URL +
                "&origins=" + origin +
                "&destinations=" + destination +
                "&key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("rows")) {
            List<Map<String, Object>> rows = (List<Map<String, Object>>) response.get("rows");
            if (!rows.isEmpty()) {
                List<Map<String, Object>> elements = (List<Map<String, Object>>) rows.get(0).get("elements");
                if (!elements.isEmpty()) {
                    Map<String, Object> distanceData = (Map<String, Object>) elements.get(0).get("distance");
                    return ((Number) distanceData.get("value")).doubleValue() / 1000; // Tránh ép kiểu double trực tiếp
                }
            }
        }

        throw new RuntimeException("Không thể lấy thông tin khoảng cách từ Google Maps API.");
    }
}