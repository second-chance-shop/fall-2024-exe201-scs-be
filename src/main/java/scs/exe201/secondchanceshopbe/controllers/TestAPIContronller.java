package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scs.exe201.secondchanceshopbe.services.Iplm.ShippingFeeService;

@RestController
@RequestMapping("/api/v1/test")
public class TestAPIContronller {
    @Autowired
    private ShippingFeeService shippingFeeService;

    // ...

    @GetMapping
    public double getShippingFee(@RequestParam String origin, @RequestParam String destination, @RequestParam double weight) {
        return shippingFeeService.calculateFee(origin, destination, weight);
    }
}
