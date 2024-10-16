package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.iplm.ShippingFeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shippingfee")
public class ShippingFeeController {
    private ShippingFeeService shippingFeeService;

    @PostMapping("/caculate")
    public ResponseEntity<ResponseObject> caculateShip(){
        
         return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_lIST_SUCCESS")
                        .message("create user successfully")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(null)
                        .build()
        );
    }
}
