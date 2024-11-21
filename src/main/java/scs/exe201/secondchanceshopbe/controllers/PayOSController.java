package scs.exe201.secondchanceshopbe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.IPayOsService;
import java.net.URI;
@RestController
@RequestMapping("/api/v1/payos")
@RequiredArgsConstructor
public class PayOSController {

    private final IPayOsService payosService;
    @GetMapping("/success")
    public ResponseEntity<ResponseObject> handleSuccess(@RequestParam long orderCode) {
        OrderResponse response = payosService.actionSuccess(orderCode);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                   //     .location(URI.create(String.format("%s?status=%s", returnResult.getCallbackUrl(), returnResult.isStatus() ? "success": "failed")))
                        .location("https://2ndchanceshop.vercel.app")
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
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .location("https://2ndchanceshop.vercel.app")
                        .code("SUCCESS")
                        .message("Order canceled")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(response)
                        .build()
        );
    }
}
