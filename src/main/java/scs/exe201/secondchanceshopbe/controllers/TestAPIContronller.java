package scs.exe201.secondchanceshopbe.controllers;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import scs.exe201.secondchanceshopbe.services.Iplm.ShippingFeeService;
import scs.exe201.secondchanceshopbe.services.Iplm.TestAPIService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestAPIContronller {
    @Autowired
    private ShippingFeeService shippingFeeService;

    // ...

    @GetMapping
    public double getShippingFee(@RequestParam String origin, @RequestParam String destination, @RequestParam double weight) {
        return shippingFeeService.calculateFee(origin, destination, weight);
    }

    private final TestAPIService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
