package scs.exe201.secondchanceshopbe.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import scs.exe201.secondchanceshopbe.models.dtos.requests.CartCreateDTO;
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
                        .code("GET_SUCCESS")
                        .message("get all success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseObject> getOrderByUserId(@RequestParam long id) {
        List <OrderResponse> orderResponse = orderService.getAllByUserId(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("get  success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @GetMapping("/user-checkout")
    public ResponseEntity<ResponseObject> getOrderHasBuyByUser() {
        List <OrderResponse> orderResponse = orderService.getAllByUserCheckout();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("get  success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @GetMapping("/user-cart")
    public ResponseEntity<ResponseObject> getOrderCartByUser() {
        List <OrderResponse> orderResponse = orderService.getAllByUserCart();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("GET_SUCCESS")
                        .message("get  success")
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
                        .message("create success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @PostMapping("/add-cart")
    public ResponseEntity<ResponseObject> createCart(@RequestBody CartCreateDTO createDTO){
        OrderResponse orderResponse = orderService.createCart(createDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("CREATE_SUCCESS")
                        .message("create success")
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
                        .code("UPDATE_SUCCESS")
                        .message("update success")
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
                        .code("DELETE_SUCCESS")
                        .message("delete success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }
    @PutMapping("/checkout/{cartId}")
    public ResponseEntity<ResponseObject> checkoutOrder(@PathVariable long cartId, @RequestParam String methodPayment){
        OrderResponse orderResponse = orderService.checkoutOrder(cartId, methodPayment);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("UPDATE_SUCCESS")
                        .message("update success")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(orderResponse)
                        .build()
        );
    }

}
