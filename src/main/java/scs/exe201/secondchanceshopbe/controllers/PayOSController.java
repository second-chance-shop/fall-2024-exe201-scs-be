package scs.exe201.secondchanceshopbe.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.IPayOsService;
@RestController
@RequestMapping("/api/v1/payos")
@RequiredArgsConstructor
public class PayOSController {

    private final IPayOsService payosService;
    @GetMapping("/success")
    public ResponseEntity<ResponseObject> handleSuccess(@RequestParam long orderCode) {
        OrderResponse response = payosService.actionSuccess(orderCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://2ndchanceshop.vercel.app")).body(
                ResponseObject.builder()
                        .code("ORDER_SUCCESS")
                        .message("Order successfully, thank you for order")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }


    @GetMapping("/cancel")
    public ResponseEntity<ResponseObject> handleCancel(@RequestParam long orderCode) {
        OrderResponse response = payosService.actionCancel(orderCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://2ndchanceshop.vercel.app/shopping-cart")).body(
                ResponseObject.builder()
                        .code("SUCCESS")
                        .message("Order canceled")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
}
