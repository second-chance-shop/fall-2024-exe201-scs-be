package scs.exe201.secondchanceshopbe.services.iplm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.services.googleService.GoogleMapsService;

@Service
public class ShippingFeeService  {
    @Autowired
    private GoogleMapsService googleMapsService;

    // Hàm tính phí ship
    public double calculateFee(String origin, String destination, double weight) {
        // 1. Lấy khoảng cách từ Google Maps
        double distance = googleMapsService.getDistance(origin, destination);

        // 2. Áp dụng công thức tính phí (ví dụ):
        double fee = distance * weight * 0.5; // Ví dụ: 0.5 là đơn giá/km/kg

        return fee;
    }
}
