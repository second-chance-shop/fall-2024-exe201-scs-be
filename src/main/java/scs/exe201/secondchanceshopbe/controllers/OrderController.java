package scs.exe201.secondchanceshopbe.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.RatingResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.OrderService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ResponseObject> getOrder() {
        List <OrderResponse> orderResponse = orderService.getAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @GetMapping("/user{id}")
    public ResponseEntity<ResponseObject> getOrderByUserId(@RequestParam long id) {
        List <OrderResponse> orderResponse = orderService.getAllByUserId(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderCreateDTO createDTO){
        OrderResponse orderResponse = orderService.createOrder(createDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateOrder(@PathVariable long id, @RequestBody OrderUpdateDTO updateDTO){
        OrderResponse orderResponse = orderService.updateOrder(id,updateDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@PathVariable long id){
        OrderResponse orderResponse = orderService.deleteOrder(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
}
