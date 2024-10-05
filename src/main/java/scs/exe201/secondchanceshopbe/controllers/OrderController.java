package scs.exe201.secondchanceshopbe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderCreateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.requests.OrderUpdateDTO;
import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.dtos.response.ResponseObject;
import scs.exe201.secondchanceshopbe.services.OrderService;
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
